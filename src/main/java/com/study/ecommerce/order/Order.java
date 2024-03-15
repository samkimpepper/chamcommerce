package com.study.ecommerce.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    private OrderOptionGroups orderOptionGroups = new OrderOptionGroups();

    public int getTotalPrice() {
        return orderOptionGroups.getTotalPrice();
    }

    public int getTotalQuantity() {
        return orderOptionGroups.getTotalQuantity();
    }

    public void setOrderOptionGroups(List<OrderOptionGroup> orderOptionGroups) {
        this.orderOptionGroups.setOrderOptionGroups(orderOptionGroups);
    }

    public static Order of(Long customerId, List<OrderOptionGroup> orderOptionGroups) {
        Order order = Order.builder()
                .customerId(customerId)
                .orderedAt(LocalDateTime.now())
                .build();

        order.setOrderOptionGroups(orderOptionGroups);
        order.setDeliveryFee(DeliveryFeeCalculator.calculate(order.getOrderOptionGroups()));

        return order;
    }
}
