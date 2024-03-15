package com.study.ecommerce.product.dto;

import com.study.ecommerce.product.domain.ProductOptionDetail;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DetailResponse {
    private Long id;
    private String value;

    public static List<DetailResponse> listOf(List<ProductOptionDetail> productOptionDetails) {
        return productOptionDetails.stream()
                .map(detail -> DetailResponse.of(detail.getId(), detail.getOptionValue()))
                .toList();
    }

    private static DetailResponse of(Long id, String value) {
        return DetailResponse.builder()
                .id(id)
                .value(value)
                .build();
    }
}
