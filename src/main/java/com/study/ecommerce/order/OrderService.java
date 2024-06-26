package com.study.ecommerce.order;

import com.study.ecommerce.delivery.domain.Delivery;
import com.study.ecommerce.member.domain.DeliveryAddress;
import com.study.ecommerce.member.DeliveryAddressRepository;
import com.study.ecommerce.member.domain.Member;
import com.study.ecommerce.member.MemberRepository;
import com.study.ecommerce.member.exception.DeliveryAddressNotFoundException;
import com.study.ecommerce.member.exception.MemberNotFoundException;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderStatus;
import com.study.ecommerce.order.dto.OrderCreateRequest;
import com.study.ecommerce.order.dto.OrderItemRequest;
import com.study.ecommerce.order.dto.OrderResponse;
import com.study.ecommerce.order.event.OrderCancelledEvent;
import com.study.ecommerce.order.event.OrderCompletedEvent;
import com.study.ecommerce.order.event.OrderCreatedEvent;
import com.study.ecommerce.order.event.OrderPlacedEvent;
import com.study.ecommerce.order.exception.OrderNotFoundException;
import com.study.ecommerce.order.repository.OrderRepository;
import com.study.ecommerce.point.PointService;
import com.study.ecommerce.point.PointUsePolicy;
import com.study.ecommerce.product.domain.ProductItem;
import com.study.ecommerce.product.domain.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductItemRepository productItemRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final PointService pointService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MemberRepository memberRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, Long customerId) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(request.getDeliveryAddressId())
                .orElseThrow(DeliveryAddressNotFoundException::new);

        List<ProductItem> productItems = productItemRepository
                .findAllById(request.getOrderOptionGroups()
                .stream()
                .map(OrderItemRequest::getProductOptionGroupId)
                .collect(Collectors.toList()));

        Map<Long, ProductItem> productItemById = productItems.stream()
                .collect(Collectors.toMap(ProductItem::getId, Function.identity()));

        List<OrderItem> orderItems = request.getOrderOptionGroups()
                .stream()
                .map(orderItemRequest -> orderItemRequest.toEntity(
                        productItemById.get(orderItemRequest.getProductOptionGroupId())
                ))
                .collect(Collectors.toList());

        int totalPrice = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();

        Member member = memberRepository.findById(customerId)
                .orElseThrow(MemberNotFoundException::new);

        // 쿠폰, 포인트 적용 로직
        if (request.getPointsToUse() > 0) {
            PointUsePolicy.isAvailable(member, totalPrice, request.getPointsToUse());
            totalPrice -= request.getPointsToUse();
        }

        Order order = Order.of(customerId, orderItems, totalPrice, request.getPaymentMethod(), deliveryAddress);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderRepository.save(order);

        pointService.usePoints(customerId, totalPrice, request.getPointsToUse(), order);

        applicationEventPublisher.publishEvent(new OrderCreatedEvent(order, orderItems));
        applicationEventPublisher.publishEvent(new OrderPlacedEvent(order, orderItems));

        return OrderResponse.of(order);
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findByIdWithOrderItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        order.cancel();

        applicationEventPublisher.publishEvent(new OrderCancelledEvent(order, order.getOrderItems()));

        return OrderResponse.of(order);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderDetail(Long orderId) {
        Order order = orderRepository.findByIdWithOrderItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        return OrderResponse.of(order);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registerDelivery(Order order, Delivery delivery) {
        order.registerDelivery(delivery);
        orderRepository.save(order);
    }

    @Transactional
    public OrderResponse completeOrder(Long orderId) {
        Order order = orderRepository.findByIdWithOrderItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        order.complete();

        applicationEventPublisher.publishEvent(new OrderCompletedEvent(order.getCustomerId(), order, order.getTotalPrice()));

        return OrderResponse.of(order);
    }

    public long getTotalAmountThisMonth(Long memberId) {
        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atStartOfDay().plusDays(1).minusSeconds(1);

        List<Order> ordersThisMonth = orderRepository.findByCustomerIdAndOrderedAtBetweenAndStatus(memberId, start, end, OrderStatus.COMPLETED);

        long totalAmountThisMonth = ordersThisMonth.stream()
                .mapToLong(Order::getTotalPrice)
                .sum();

        return totalAmountThisMonth;
    }

    public int getTotalCountThisMonth(Long memberId) {
        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atStartOfDay().plusDays(1).minusSeconds(1);

        List<Order> ordersThisMonth = orderRepository.findByCustomerIdAndOrderedAtBetweenAndStatus(memberId, start, end, OrderStatus.COMPLETED);

        return ordersThisMonth.size();
    }
}
