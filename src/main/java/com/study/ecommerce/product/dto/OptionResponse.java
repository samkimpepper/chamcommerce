package com.study.ecommerce.product.dto;

import com.study.ecommerce.product.domain.ProductOption;
import com.study.ecommerce.product.domain.ProductOptionDetail;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OptionResponse {
    private Long id;
    private String name;
    private List<DetailResponse> details;

    public static List<OptionResponse> listOf(List<ProductOption> options) {
        return options.stream()
                .map(option -> OptionResponse.of(option, option.getProductOptionDetails()))
                .toList();
    }

    public static OptionResponse of(ProductOption option, List<ProductOptionDetail> details) {
        return OptionResponse.builder()
                .id(option.getId())
                .name(option.getName())
                .details(DetailResponse.listOf(details))
                .build();
    }
}
