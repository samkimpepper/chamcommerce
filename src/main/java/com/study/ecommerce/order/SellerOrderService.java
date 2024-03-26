package com.study.ecommerce.order;

import com.study.ecommerce.delivery.domain.Delivery;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderItems;
import com.study.ecommerce.order.domain.SellerOrder;
import com.study.ecommerce.order.dto.SellerOrderResponse;
import com.study.ecommerce.order.event.OrderShippedEvent;
import com.study.ecommerce.order.repository.OrderItemRepository;
import com.study.ecommerce.order.repository.OrderRepository;
import com.study.ecommerce.order.repository.SellerOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerOrderService {
    private final SellerOrderRepository sellerOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createSellerOrder(Long sellerId, Order order, List<OrderItem> orderItems) {
        orderItems = orderItemRepository.saveAll(orderItems);

        SellerOrder sellerOrder = SellerOrder.of(sellerId, order, orderItems);
        sellerOrderRepository.save(sellerOrder);
    }

    @Transactional(readOnly = true)
    public List<SellerOrderResponse> getAllSellerOrders(Long sellerId) {
        List<SellerOrder> sellerOrders = sellerOrderRepository.findAllBySellerId(sellerId);

        return sellerOrders.stream()
                .map(SellerOrderResponse::of)
                .toList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelSellerOrders(OrderItems orderItems) {
        Set<SellerOrder> sellerOrders = orderItems.getSellerOrders();
        sellerOrders.forEach(SellerOrder::cancel);
        sellerOrderRepository.saveAll(sellerOrders);
    }

    @Transactional
    public SellerOrderResponse shipOrder(Long sellerId, Long sellerOrderId) {
        SellerOrder sellerOrder = sellerOrderRepository.findById(sellerOrderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        if (!sellerOrder.isOwner(sellerId)) {
            throw new IllegalArgumentException("주문을 처리할 수 없습니다.");
        }

        sellerOrder.ship();

        eventPublisher.publishEvent(new OrderShippedEvent(sellerOrder));

        return SellerOrderResponse.of(sellerOrder);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registerDelivery(SellerOrder sellerOrder, Delivery delivery) {
        sellerOrder.registerDelivery(delivery);
        sellerOrderRepository.save(sellerOrder);
    }
}
