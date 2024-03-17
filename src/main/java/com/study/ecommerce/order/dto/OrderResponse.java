package com.study.ecommerce.order.dto;

import com.study.ecommerce.order.domain.Order;
import lombok.Builder;
import lombok.Getter;

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
    private String status;
    private LocalDateTime orderedAt;
    private List<OrderItemResponse> orderOptions;

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .totalPrice(order.getTotalPrice())
                .totalQuantity(order.getTotalQuantity())
                .deliveryFee(order.getDeliveryFee())
                .status(order.getStatus().name())
                .orderedAt(order.getOrderedAt())
                .orderOptions(OrderItemResponse.listOf(order.getOrderItems()))
                .build();
    }
}
