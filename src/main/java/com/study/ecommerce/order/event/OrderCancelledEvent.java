package com.study.ecommerce.order.event;

import com.study.ecommerce.notification.Notification;
import com.study.ecommerce.notification.NotificationEvent;
import com.study.ecommerce.notification.NotificationType;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class OrderCancelledEvent {
    private Order order;
    private OrderItems orderItems;

    public Notification toNotification() {
        String content = orderItems.get(0).getProductName() + "외 " + orderItems.size() + "건의 주문이 취소되었습니다.";
        String link = "/orders/" + order.getId();

        return Notification.builder()
                .content(content)
                .link(link)
                .type(NotificationType.ORDER_CANCELLED)
                .createdAt(order.getCancelledAt())
                .receiverId(order.getCustomerId())
                .build();
    }
}
