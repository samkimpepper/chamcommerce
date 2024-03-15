package com.study.ecommerce.product.dto;

import com.study.ecommerce.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductCreateRequest {
    private String name;
    private int price;
    private int deliveryFee;
    private List<OptionCreateRequest> options;

    public Product toEntity(Long sellerId) {
        return Product.builder()
                .name(name)
                .deliveryFee(deliveryFee)
                .sellerId(sellerId)
                .build();
    }
}
