package com.study.ecommerce.product.dto;

import com.study.ecommerce.product.domain.Product;
import com.study.ecommerce.product.domain.ProductOption;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private int deliveryFee;
    private int defaultPrice;
    private int totalStock;
    private List<ProductItemResponse> optionGroups;
    private List<OptionResponse> options;

    public static ProductResponse of(Product product, List<ProductOption> options) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .deliveryFee(product.getDeliveryFee())
                .defaultPrice(product.getDefaultPrice())
                .totalStock(product.getTotalStock())
                .optionGroups(ProductItemResponse.listOf(product.getProductItems()))
                .options(OptionResponse.listOf(options))
                .build();
    }
}
