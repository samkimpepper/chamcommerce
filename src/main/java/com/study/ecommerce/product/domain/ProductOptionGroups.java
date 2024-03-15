package com.study.ecommerce.product.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@Embeddable
public class ProductOptionGroups implements Iterable<ProductOptionGroup> {
    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<ProductOptionGroup> productOptionGroups = new ArrayList<>();


    @Override
    public Iterator<ProductOptionGroup> iterator() {
        return productOptionGroups.iterator();
    }

    public Stream<ProductOptionGroup> stream() {
        return productOptionGroups.stream();
    }

    public void add(ProductOptionGroup productOptionGroup) {
        productOptionGroups.add(productOptionGroup);
    }

    public int size() {
        return productOptionGroups.size();
    }

    public int getTotalStock() {
        return productOptionGroups.stream()
                .mapToInt(ProductOptionGroup::getStock)
                .sum();
    }

    public int getMinimumPrice() {
        return productOptionGroups.stream()
                .mapToInt(ProductOptionGroup::getPrice)
                .min()
                .orElse(0);
    }
}
