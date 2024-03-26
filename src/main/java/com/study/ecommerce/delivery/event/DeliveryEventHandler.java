package com.study.ecommerce.delivery.event;

import com.study.ecommerce.notification.NotificationService;
import com.study.ecommerce.order.OrderDeliveryService;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.SellerOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class DeliveryEventHandler {
    private final NotificationService notificationService;
    private final OrderDeliveryService orderDeliveryService;

    @EventListener
    public void onDeliveryStarted(DeliveryStartedEvent event) {

        notificationService.sendNotification(event.toNotification());
    }

    @TransactionalEventListener
    public void onDelivered(DeliveredEvent event) {
        Order order = event.getDelivery().getOrder();
        SellerOrder sellerOrder = event.getDelivery().getSellerOrder();

        orderDeliveryService.completeDelivery(order, sellerOrder);

        notificationService.sendNotification(event.toNotification());
    }
}
