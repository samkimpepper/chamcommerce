package com.study.ecommerce.product.dto;

import com.study.ecommerce.product.domain.ProductOption;
import com.study.ecommerce.product.domain.ProductOptionDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DetailCreateRequest {
    private String optionValue;
    private String description;

    public ProductOptionDetail toEntity(ProductOption option) {
        return ProductOptionDetail.builder()
                .optionValue(optionValue)
                .productOption(option)
                .build();
    }
}
