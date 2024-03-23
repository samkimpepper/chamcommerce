package com.study.ecommerce.order.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@Embeddable
public class SellerOrderItems implements Iterable<OrderItem> {
    @OneToMany(mappedBy = "sellerOrder", orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Override
    public Iterator<OrderItem> iterator() {
        return orderItems.iterator();
    }

    public Stream<OrderItem> stream() {
        return orderItems.stream();
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void add(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public int size() {
        return orderItems.size();
    }

    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    public int getTotalQuantity() {
        return orderItems.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
    }
}
