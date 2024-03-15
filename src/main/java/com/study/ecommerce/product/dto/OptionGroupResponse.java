package com.study.ecommerce.product.dto;

import com.study.ecommerce.product.domain.ProductOptionGroup;
import com.study.ecommerce.product.domain.ProductOptionGroups;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Builder
public class OptionGroupResponse {
    private Long id;

    private List<DetailResponse> details;

    private int price;

    private int stock;

    public static List<OptionGroupResponse> listOf(ProductOptionGroups productOptionGroups ) {
        return productOptionGroups.stream()
                .map(OptionGroupResponse::of)
                .collect(toList());
    }

    private static OptionGroupResponse of(ProductOptionGroup productOptionGroups) {
        return OptionGroupResponse.builder()
                .id(productOptionGroups.getId())
                .details(DetailResponse.listOf(productOptionGroups.getProductOptionDetails()))
                .price(productOptionGroups.getPrice())
                .stock(productOptionGroups.getStock())
                .build();
    }
}
