package com.study.ecommerce.payment;

import com.study.ecommerce.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventHandler {
    private final NotificationService notificationService;

    @EventListener
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        notificationService.sendNotification(event.toNotification());
    }
}
