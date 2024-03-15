package com.study.ecommerce.product.domain;

import com.study.ecommerce.member.Member;
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
    private ProductOptionGroups productOptionGroups = new ProductOptionGroups();

    public int getDefaultPrice() {
        return productOptionGroups.getMinimumPrice();
    }

    public int getTotalStock() {
        return productOptionGroups.getTotalStock();
    }

    public void addOptionGroup(ProductOptionGroup productOptionGroup) {
        productOptionGroups.add(productOptionGroup);
    }
}
