package com.study.ecommerce.order.dto;

import com.study.ecommerce.order.OrderOptionGroup;
import com.study.ecommerce.order.OrderOptionGroups;
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

    public static List<OrderOptionGroupResponse> listOf(OrderOptionGroups orderOptionGroups) {
        return orderOptionGroups.stream()
                .map(OrderOptionGroupResponse::of)
                .toList();
    }

    public static OrderOptionGroupResponse of(OrderOptionGroup orderOptionGroup) {
        return OrderOptionGroupResponse.builder()
                .productName(orderOptionGroup.getProductName())
                .optionName(orderOptionGroup.getOptionName())
                .quantity(orderOptionGroup.getQuantity())
                .price(orderOptionGroup.getPrice())
                .totalPrice(orderOptionGroup.getTotalPrice())
                .build();
    }
}
