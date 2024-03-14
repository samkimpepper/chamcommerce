package com.study.ecommerce.product.dto;

import com.study.ecommerce.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private int deliveryFee;
    private int defaultPrice;
    private int totalStock;

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .deliveryFee(product.getDeliveryFee())
                .defaultPrice(product.getDefaultPrice())
                .totalStock(product.getTotalStock())
                .build();
    }
}
