package com.study.ecommerce.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orders")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private LocalDateTime orderedAt;

    @Setter
    private int deliveryFee;

    @Embedded
    @Builder.Default
    private OrderItems orderItems = new OrderItems();

    public int getTotalPrice() {
        return orderItems.getTotalPrice();
    }

    public int getTotalQuantity() {
        return orderItems.getTotalQuantity();
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems.setOrderOptionGroups(orderItems);
    }

    public static Order of(Long customerId, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .customerId(customerId)
                .orderedAt(LocalDateTime.now())
                .build();

        order.setOrderItems(orderItems);
        order.setDeliveryFee(DeliveryFeeCalculator.calculate(order.getOrderItems()));

        return order;
    }
}
