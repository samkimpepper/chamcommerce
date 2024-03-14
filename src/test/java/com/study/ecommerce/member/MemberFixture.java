package com.study.ecommerce.member;

import com.study.ecommerce.member.dto.SignupRequest;

import java.util.Set;

public class MemberFixture {
    public static final String CUSTOMER_EMAIL = "customer@customer.com";
    public static final String PASSWORD = "password";

    public static final String SELLER_EMAIL = "seller@seller.com";

    public static SignupRequest createCustomer() {
        return new SignupRequest(CUSTOMER_EMAIL, PASSWORD, "ROLE_CUSTOMER");
    }

    public static SignupRequest createSeller() {
        return new SignupRequest(SELLER_EMAIL, PASSWORD, "ROLE_SELLER");
    }

    public static Member createAdmin() {
        return Member.builder()
                .email("admin@admin.com")
                .password("password")
                .roles(Set.of(Role.ROLE_ADMIN))
                .build();
    }
}
