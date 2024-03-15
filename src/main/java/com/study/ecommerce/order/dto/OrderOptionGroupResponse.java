package com.study.ecommerce.order.dto;

import com.study.ecommerce.order.OrderItem;
import com.study.ecommerce.order.OrderItems;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderOptionGroupResponse {
    private String productName;
    private String optionName;
    private int quantity;
    private int price;
    private int totalPrice;

    public static List<OrderOptionGroupResponse> listOf(OrderItems orderItems) {
        return orderItems.stream()
                .map(OrderOptionGroupResponse::of)
                .toList();
    }

    public static OrderOptionGroupResponse of(OrderItem orderItem) {
        return OrderOptionGroupResponse.builder()
                .productName(orderItem.getProductName())
                .optionName(orderItem.getOptionName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}
