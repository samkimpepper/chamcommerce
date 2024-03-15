package com.study.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int deliveryFee;

    private Long sellerId;

    @Embedded
    @Builder.Default
    private ProductItems productItems = new ProductItems();

    public int getDefaultPrice() {
        return productItems.getMinimumPrice();
    }

    public int getTotalStock() {
        return productItems.getTotalStock();
    }

    public void addOptionGroup(ProductItem productItem) {
        productItems.add(productItem);
    }
}
