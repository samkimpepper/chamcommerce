package com.study.ecommerce.product.dto;

import com.study.ecommerce.product.domain.Product;
import com.study.ecommerce.product.domain.ProductOptionDetail;
import com.study.ecommerce.product.domain.ProductOptionGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OptionGroupCreateRequest {
    private List<Long> detailIds;
    private int price;
    private int stock;

    public ProductOptionGroup toEntity(Product product, List<ProductOptionDetail> details) {
        return ProductOptionGroup.builder()
                .product(product)
                .productOptionDetails(details)
                .price(price)
                .stock(stock)
                .build();
    }
}
