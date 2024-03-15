package com.study.ecommerce.product.dto;

import com.study.ecommerce.product.domain.ProductItem;
import com.study.ecommerce.product.domain.ProductItems;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Builder
public class ProductItemResponse {
    private Long id;

    private List<DetailResponse> details;

    private int price;

    private int stock;

    public static List<ProductItemResponse> listOf(ProductItems productItems) {
        return productItems.stream()
                .map(ProductItemResponse::of)
                .collect(toList());
    }

    private static ProductItemResponse of(ProductItem productOptionGroups) {
        return ProductItemResponse.builder()
                .id(productOptionGroups.getId())
                .details(DetailResponse.listOf(productOptionGroups.getProductOptionDetails()))
                .price(productOptionGroups.getPrice())
                .stock(productOptionGroups.getStock())
                .build();
    }
}
