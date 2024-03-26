package com.study.ecommerce.order.event;

import com.study.ecommerce.delivery.domain.Delivery;
import com.study.ecommerce.notification.Notification;
import com.study.ecommerce.notification.NotificationType;
import com.study.ecommerce.order.domain.SellerOrder;
import com.study.ecommerce.order.domain.SellerOrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderShippedEvent {
    private SellerOrder sellerOrder;

    public Notification toNotification(Delivery delivery) {
        String content = delivery.getProductNames() + "의 주문이 출고되었습니다.";
        String link = "/orders/" + sellerOrder.getOrder().getId();

        return Notification.builder()
                .content(content)
                .link(link)
                .type(NotificationType.ORDER_SHIPPED)
                .createdAt(delivery.getShippedAt())
                .receiverId(sellerOrder.getOrder().getCustomerId())
                .build();
    }
}
