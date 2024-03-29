package com.study.ecommerce.order.event;

import com.study.ecommerce.delivery.DeliveryService;
import com.study.ecommerce.delivery.domain.Delivery;
import com.study.ecommerce.notification.NotificationService;
import com.study.ecommerce.order.OrderService;
import com.study.ecommerce.order.SellerOrderService;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderItems;
import com.study.ecommerce.order.domain.SellerOrder;
import com.study.ecommerce.point.PointService;
import com.study.ecommerce.point.strategy.PurchasePointStrategy;
import com.study.ecommerce.product.domain.ProductItem;
import com.study.ecommerce.product.domain.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventHandler {
    private final ProductItemRepository productItemRepository;
    private final NotificationService notificationService;
    private final SellerOrderService sellerOrderService;
    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final PointService pointService;

    @TransactionalEventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        List<OrderItem> orderItems = event.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            ProductItem productItem = orderItem.getProductItem();
            productItem.decreaseStock(orderItem.getQuantity());
            productItemRepository.save(productItem);
        }

        notificationService.sendNotification(event.toNotification());
    }

    @TransactionalEventListener
    public void onOrderCancelled(OrderCancelledEvent event) {
        OrderItems orderItems = event.getOrderItems();

        for (OrderItem orderItem : orderItems) {

            ProductItem productItem = orderItem.getProductItem();
            productItem.increaseStock(orderItem.getQuantity());
            productItemRepository.save(productItem);
        }

        sellerOrderService.cancelSellerOrders(orderItems);

        pointService.refundPoints(event.getOrder());

        notificationService.sendNotification(event.toNotification());
    }

    @TransactionalEventListener
    public void onOrderPlaced(OrderPlacedEvent event) {
        Map<Long, List<OrderItem>> orderItemsBySeller = event.getOrderItems().stream()
                .collect(groupingBy(orderItem -> orderItem.getProductItem().getProduct().getSellerId()));

        orderItemsBySeller.entrySet()
                        .forEach(entry -> {
                            Long sellerId = entry.getKey();
                            List<OrderItem> orderItems = entry.getValue();
                            sellerOrderService.createSellerOrder(sellerId, event.getOrder(), orderItems);
                        });

        notificationService.sendNotifications(event.toNotifications());
    }

    @TransactionalEventListener
    public void onOrderShipped(OrderShippedEvent event) {
        SellerOrder sellerOrder = event.getSellerOrder();
        Order order = sellerOrder.getOrder();

        Delivery delivery = deliveryService.register(order, sellerOrder);

        orderService.registerDelivery(order, delivery);
        sellerOrderService.registerDelivery(sellerOrder, delivery);

        notificationService.sendNotification(event.toNotification(delivery));
    }

    @TransactionalEventListener
    public void onOrderCompleted(OrderCompletedEvent event) {
        Long memberId = event.getMemberId();
        Order order = event.getOrder();
        int amount = event.getTotalAmount();

        pointService.earnPoints(memberId, amount, new PurchasePointStrategy(), order);
    }
}
