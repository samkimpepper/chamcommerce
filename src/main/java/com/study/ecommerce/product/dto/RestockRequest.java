package com.study.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestockRequest {
    private Long productItemId;
    private int quantity;
}
