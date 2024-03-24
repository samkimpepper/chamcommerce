package com.study.ecommerce.order.dto;

import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.product.domain.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemRequest {
    private Long productOptionGroupId;
    private int quantity;

    public OrderItem toEntity(ProductItem productItem) {
        String optionName = productItem.getProductOptionDetails().stream()
                .map(productOptionDetail -> productOptionDetail.getProductOption().getName() + ": " + productOptionDetail.getOptionValue())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return OrderItem.builder()
                .productItem(productItem)
                .sellerOrder(null)
                .quantity(quantity)
                .price(productItem.getPrice())
                .productName(productItem.getProduct().getName())
                .optionName(optionName)
                .build();
    }
}
