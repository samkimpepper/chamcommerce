package com.study.ecommerce.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptionResponse {
    private Long id;
    private String name;
}
