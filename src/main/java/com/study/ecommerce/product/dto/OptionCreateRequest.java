package com.study.ecommerce.product.dto;

import com.study.ecommerce.product.domain.Product;
import com.study.ecommerce.product.domain.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class OptionCreateRequest {
    private String name;
    private List<DetailCreateRequest> details;

    public ProductOption toEntity(Product product) {
        return ProductOption.builder()
                .name(name)
                .product(product)
                .build();
    }
}
