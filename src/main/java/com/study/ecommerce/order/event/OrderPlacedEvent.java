package com.study.ecommerce.order.event;

import com.study.ecommerce.notification.Notification;
import com.study.ecommerce.notification.NotificationEvent;
import com.study.ecommerce.notification.NotificationType;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
public class OrderPlacedEvent {
    private Order order;
    private List<OrderItem> orderItems;

    public List<Notification> toNotifications() {
        Map<Long, List<OrderItem>> orderItemsBySeller = orderItems.stream()
                .collect(groupingBy(orderItem -> orderItem.getProductItem().getProduct().getSellerId()));

        return orderItemsBySeller.entrySet().stream()
                .map(entry -> {
                    Long sellerId = entry.getKey();
                    List<OrderItem> orderItems = entry.getValue();

                    String content = orderItems.get(0).getProductName() + "외 " + orderItems.size() + "건의 주문이 발생되었습니다.";
                    String link = "/orders/" + order.getId();

                    return Notification.builder()
                            .content(content)
                            .link(link)
                            .type(NotificationType.ORDER_PLACED)
                            .createdAt(order.getOrderedAt())
                            .receiverId(sellerId)
                            .build();
                })
                .collect(toList());
    }
}
