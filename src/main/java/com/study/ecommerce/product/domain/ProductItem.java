package com.study.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "productItem")
    private List<ProductOptionDetail> productOptionDetails = new ArrayList<>();

    @ManyToOne
    private Product product;

    private int stock;

    private int price;

    public void increaseStock(int quantity) {
        this.stock += quantity;
    }

    public void decreaseStock(int quantity) {
        int restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock = restStock;
    }
}
