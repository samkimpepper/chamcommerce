package com.study.ecommerce.delivery.event;

import com.study.ecommerce.delivery.domain.Delivery;
import com.study.ecommerce.notification.Notification;
import com.study.ecommerce.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DeliveryStartedEvent {
    private Delivery delivery;

    public Notification toNotification() {
        String content = delivery.getProductNames() + "배송이 시작되었습니다.";
        String link = "/orders/" + delivery.getOrder().getId();

        return Notification.builder()
                .content(content)
                .link(link)
                .type(NotificationType.DELIVERY_STARTED)
                .createdAt(LocalDateTime.now())
                .receiverId(delivery.getOrder().getCustomerId())
                .build();
    }
}
