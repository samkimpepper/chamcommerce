package com.study.ecommerce.order.event;


import com.study.ecommerce.notification.Notification;
import com.study.ecommerce.notification.NotificationEvent;
import com.study.ecommerce.notification.NotificationType;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.product.domain.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderCreatedEvent {
    private Order order;
    private List<OrderItem> orderItems;

    public Notification toNotification() {
        String content = orderItems.get(0).getProductName() + "외 " + orderItems.size() + "건의 주문이 완료되었습니다.";
        String link = "/orders/" + order.getId();

        return Notification.builder()
                .content(content)
                .link(link)
                .type(NotificationType.ORDER_COMPLETED)
                .createdAt(order.getOrderedAt())
                .receiverId(order.getCustomerId())
                .build();
    }
}
