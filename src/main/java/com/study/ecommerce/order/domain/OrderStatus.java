package com.study.ecommerce.order.domain;

public enum OrderStatus {
    WAITING_FOR_PAYMENT, ORDERED, COMPLETED, CANCELLED;

    public boolean isCanceled() {
        return this == CANCELLED;
    }

    public boolean isOrdered() {
        return this == ORDERED;
    }
}
