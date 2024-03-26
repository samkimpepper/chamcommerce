package com.study.ecommerce.order.domain;

public enum OrderStatus {
    WAITING_FOR_PAYMENT, ORDERED, COMPLETED, CANCELLED, DELIVERING, DELIVERED;

    public boolean isCanceled() {
        return this == CANCELLED;
    }

    public boolean isOrdered() {
        return this == ORDERED;
    }

    public boolean isDelivering() {
        return this == DELIVERING;
    }

    public boolean isDelivered() {
        return this == DELIVERED;
    }
}
