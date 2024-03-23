package com.study.ecommerce.order.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@Embeddable
public class OrderItems implements Iterable<OrderItem> {
    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Override
    public Iterator<OrderItem> iterator() {
        return orderItems.iterator();
    }

    public OrderItem get(int index) {
        return orderItems.get(index);
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

    public int getTotalDeliveryFee() {
        return 0;
    }
}
