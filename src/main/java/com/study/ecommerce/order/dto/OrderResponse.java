package com.study.ecommerce.order.dto;

import com.study.ecommerce.order.Order;
import com.study.ecommerce.order.OrderOptionGroups;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponse {
    private Long id;
    private Long customerId;
    private int totalPrice;
    private int totalQuantity;
    private int deliveryFee;
    private LocalDateTime orderedAt;
    private List<OrderOptionGroupResponse> orderOptions;

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .totalPrice(order.getTotalPrice())
                .totalQuantity(order.getTotalQuantity())
                .deliveryFee(order.getDeliveryFee())
                .orderedAt(order.getOrderedAt())
                .orderOptions(OrderOptionGroupResponse.listOf(order.getOrderOptionGroups()))
                .build();
    }
}
