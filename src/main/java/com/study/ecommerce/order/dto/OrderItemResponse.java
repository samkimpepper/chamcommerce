package com.study.ecommerce.order.dto;

import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderItems;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderItemResponse {
    private String productName;
    private String optionName;
    private int quantity;
    private int price;
    private int totalPrice;

    public static List<OrderItemResponse> listOf(OrderItems orderItems) {
        return orderItems.stream()
                .map(OrderItemResponse::of)
                .toList();
    }

    public static OrderItemResponse of(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .productName(orderItem.getProductName())
                .optionName(orderItem.getOptionName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}
