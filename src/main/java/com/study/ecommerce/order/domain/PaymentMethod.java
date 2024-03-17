package com.study.ecommerce.order.domain;

public enum PaymentMethod {
    INSTANT, DEFERRED;

    public static PaymentMethod of(String paymentMethod) {
        if (paymentMethod == null || paymentMethod.isBlank()) {
            throw new IllegalArgumentException("paymentMethod must not be null");
        }

        return PaymentMethod.valueOf(paymentMethod.toUpperCase());
    }
}
