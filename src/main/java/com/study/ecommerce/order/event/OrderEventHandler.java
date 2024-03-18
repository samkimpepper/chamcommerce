package com.study.ecommerce.order.event;

import com.study.ecommerce.notification.NotificationService;
import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderItems;
import com.study.ecommerce.product.domain.ProductItem;
import com.study.ecommerce.product.domain.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderEventHandler {
    private final ProductItemRepository productItemRepository;
    private final NotificationService notificationService;

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        List<OrderItem> orderItems = event.getOrderItems();

        for (OrderItem orderItem : orderItems) {

            ProductItem productItem = orderItem.getProductItem();
            productItem.decreaseStock(orderItem.getQuantity());
            productItemRepository.save(productItem);
        }

        notificationService.sendNotification(event.toNotification());
    }

    @EventListener
    public void onOrderCancelled(OrderCancelledEvent event) {
        OrderItems orderItems = event.getOrderItems();

        for (OrderItem orderItem : orderItems) {

            ProductItem productItem = orderItem.getProductItem();
            productItem.increaseStock(orderItem.getQuantity());
            productItemRepository.save(productItem);
        }

        notificationService.sendNotification(event.toNotification());
    }

    @EventListener
    public void onOrderPlaced(OrderPlacedEvent event) {
        notificationService.sendNotifications(event.toNotifications());
    }
}
