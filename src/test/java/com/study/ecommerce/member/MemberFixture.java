package com.study.ecommerce.member;

import com.study.ecommerce.member.dto.DeliveryAddressCreateRequest;
import com.study.ecommerce.member.dto.SignupRequest;

import java.util.Set;

public class MemberFixture {
    public static final String CUSTOMER_EMAIL = "customer@customer.com";
    public static final String PASSWORD = "password";

    public static final String SELLER_EMAIL = "seller@seller.com";
    public static final String SELLER2_EMAIL = "seller2@seller.com";

    public static final String DELIVERY_WORKER_EMAIL = "delivery@delivery.com";

    public static final String ADMIN_EMAIL = "admin@admin.com";

    public static final String postCode = "12345";

    public static final String roadAddress = "서울시 강남구 역삼동";

    public static final String detailAddress = "123-456";



    public static SignupRequest createCustomer() {
        return new SignupRequest(CUSTOMER_EMAIL, PASSWORD, "ROLE_CUSTOMER");
    }

    public static SignupRequest createSeller() {
        return new SignupRequest(SELLER_EMAIL, PASSWORD, "ROLE_SELLER");
    }

    public static SignupRequest createSeller2() {
        return new SignupRequest(SELLER2_EMAIL, PASSWORD, "ROLE_SELLER");
    }

    public static SignupRequest createAdmin() {
        return new SignupRequest(ADMIN_EMAIL, PASSWORD, "ROLE_ADMIN");
    }

    public static SignupRequest createDeliveryWorker() {
        return new SignupRequest(DELIVERY_WORKER_EMAIL, PASSWORD, "ROLE_DELIVERY");
    }

    public static DeliveryAddressCreateRequest createDefaultDeliveryAddress() {
        return new DeliveryAddressCreateRequest(postCode, roadAddress, detailAddress, "memo", "홍길동", "010-1234-5678", true);
    }

    public static DeliveryAddressCreateRequest createExtraDeliveryAddress() {
        return new DeliveryAddressCreateRequest(postCode, roadAddress, detailAddress, "memo", "홍길동", "010-1234-5678", false);
    }
}
