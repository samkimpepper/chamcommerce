package com.study.ecommerce.order;

import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.dto.OrderCreateRequest;
import com.study.ecommerce.order.dto.OrderItemRequest;
import com.study.ecommerce.order.dto.OrderResponse;
import com.study.ecommerce.order.event.OrderCancelledEvent;
import com.study.ecommerce.order.event.OrderCreatedEvent;
import com.study.ecommerce.order.event.OrderPlacedEvent;
import com.study.ecommerce.product.domain.ProductItem;
import com.study.ecommerce.product.domain.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductItemRepository productItemRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderResponse createOrder(OrderCreateRequest request, Long customerId) {
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

        Order order = Order.of(customerId, orderItems, request.getPaymentMethod());

        orderRepository.save(order);

        applicationEventPublisher.publishEvent(new OrderCreatedEvent(order, orderItems));
        applicationEventPublisher.publishEvent(new OrderPlacedEvent(order, orderItems));

        return OrderResponse.of(order);
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        order.cancel();

        applicationEventPublisher.publishEvent(new OrderCancelledEvent(order, order.getOrderItems()));

        return OrderResponse.of(order);
    }
}
