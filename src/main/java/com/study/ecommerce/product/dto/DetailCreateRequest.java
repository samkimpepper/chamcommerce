package com.study.ecommerce.product.dto;

import com.study.ecommerce.product.domain.ProductOption;
import com.study.ecommerce.product.domain.ProductOptionDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DetailCreateRequest {
    private String value;

    public ProductOptionDetail toEntity(ProductOption option) {
        return ProductOptionDetail.builder()
                .optionValue(value)
                .productOption(option)
                .build();
    }
}
