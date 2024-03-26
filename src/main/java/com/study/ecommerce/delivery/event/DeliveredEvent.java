package com.study.ecommerce.delivery.event;

import com.study.ecommerce.delivery.domain.Delivery;
import com.study.ecommerce.notification.Notification;
import com.study.ecommerce.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveredEvent {
    private Delivery delivery;

    public Notification toNotification() {
        String content = delivery.getProductNames() + "이(가) 배송되었습니다.";
        String link = "/orders/" + delivery.getOrder().getId();

        return Notification.builder()
                .content(content)
                .link(link)
                .type(NotificationType.DELIVERED)
                .createdAt(delivery.getDeliveredAt())
                .receiverId(delivery.getOrder().getCustomerId())
                .build();
    }
}
