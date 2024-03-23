package com.study.ecommerce.order.dto;

import com.study.ecommerce.order.domain.SellerOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SellerOrderResponse {
    private Long id;
    private Long sellerId;
    private int totalPrice;
    private String status;
    private String orderedAt;
    private List<OrderItemResponse> orderItems;

    public static SellerOrderResponse of(SellerOrder sellerOrder) {
        return SellerOrderResponse.builder()
                .id(sellerOrder.getId())
                .sellerId(sellerOrder.getSellerId())
                .totalPrice(sellerOrder.getTotalPrice())
                .status(sellerOrder.getStatus().name())
                .orderedAt(sellerOrder.getOrderedAt().toString())
                .orderItems(OrderItemResponse.listOf(sellerOrder.getOrderItems()))
                .build();
    }
}
