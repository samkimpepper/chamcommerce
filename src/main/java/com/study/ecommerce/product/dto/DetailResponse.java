package com.study.ecommerce.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DetailResponse {
    private Long id;
    private String value;
    private int price;
    private int stock;
}
