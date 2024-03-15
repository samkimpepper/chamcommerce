package com.study.ecommerce.product.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@Embeddable
public class ProductItems implements Iterable<ProductItem> {
    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<ProductItem> productItems = new ArrayList<>();


    @Override
    public Iterator<ProductItem> iterator() {
        return productItems.iterator();
    }

    public Stream<ProductItem> stream() {
        return productItems.stream();
    }

    public void add(ProductItem productItem) {
        productItems.add(productItem);
    }

    public int size() {
        return productItems.size();
    }

    public int getTotalStock() {
        return productItems.stream()
                .mapToInt(ProductItem::getStock)
                .sum();
    }

    public int getMinimumPrice() {
        return productItems.stream()
                .mapToInt(ProductItem::getPrice)
                .min()
                .orElse(0);
    }
}
