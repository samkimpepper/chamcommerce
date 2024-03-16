package com.study.ecommerce.order.domain;

public enum OrderStatus {
    ORDERED, SHIPPING, DELIVERED, CANCELLED;

    public boolean isShipping() {
        return this == SHIPPING;
    }

    public boolean isDelivered() {
        return this == DELIVERED;
    }

    public boolean isCanceled() {
        return this == CANCELLED;
    }

    public boolean isOrdered() {
        return this == ORDERED;
    }
}
