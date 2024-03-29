package com.study.ecommerce.member.domain;

public enum Role {
    ROLE_CUSTOMER,
    ROLE_SELLER,

    ROLE_DELIVERY,
    ROLE_ADMIN;

    public static Role fromString(String role) {
        if (role == null || role.isEmpty()) {
            return ROLE_CUSTOMER;
        }
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("");
        }
    }
}
