package com.study.ecommerce.order.dto;

import com.study.ecommerce.order.OrderOptionGroup;
import com.study.ecommerce.product.domain.ProductOptionGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderOptionGroupRequest {
    private Long productOptionGroupId;
    private int quantity;

    public OrderOptionGroup toEntity(ProductOptionGroup productOptionGroup) {
        String optionName = productOptionGroup.getProductOptionDetails().stream()
                .map(productOptionDetail -> productOptionDetail.getProductOption().getName() + ": " + productOptionDetail.getOptionValue())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return OrderOptionGroup.builder()
                .productOptionGroup(productOptionGroup)
                .quantity(quantity)
                .price(productOptionGroup.getPrice())
                .productName(productOptionGroup.getProduct().getName())
                .optionName(optionName)
                .build();
    }
}
