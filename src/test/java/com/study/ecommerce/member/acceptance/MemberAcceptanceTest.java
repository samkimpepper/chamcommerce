package com.study.ecommerce.member.acceptance;

import com.study.ecommerce.AcceptanceTest;
import com.study.ecommerce.member.MemberFixture;
import com.study.ecommerce.member.dto.DeliveryAddressResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MemberAcceptanceTest extends AcceptanceTest {
    @BeforeEach
    public void setUp() {
        super.setUp();

    }

    @Test
    public void createDeliveryAddressSuccess() {
        // given
        DeliveryAddressResponse response = MemberSteps.createDeliveryAddress(ACCESS_TOKEN_CUSTOMER, MemberFixture.createDefaultDeliveryAddress());
        // when
        // then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getPostCode()).isEqualTo(MemberFixture.postCode);
        assertThat(response.isMain()).isTrue();
    }

    @Test
    public void createMultipleDeliveryAddressSuccess() {
        // given
        DeliveryAddressResponse extraDeliveryAddress = MemberSteps.createDeliveryAddress(ACCESS_TOKEN_CUSTOMER, MemberFixture.createDefaultDeliveryAddress());
        DeliveryAddressResponse defaultDeliveryAddress = MemberSteps.createDeliveryAddress(ACCESS_TOKEN_CUSTOMER, MemberFixture.createDefaultDeliveryAddress());
        // when
        List<DeliveryAddressResponse> deliveryAddresses = MemberSteps.getDeliveryAddresses(ACCESS_TOKEN_CUSTOMER);

        // then
        assertThat(deliveryAddresses.size()).isEqualTo(2);
        assertThat(MemberSteps.findDeliveryAddressById(deliveryAddresses, extraDeliveryAddress.getId()).isMain()).isFalse();
        assertThat(MemberSteps.findDeliveryAddressById(deliveryAddresses, defaultDeliveryAddress.getId()).isMain()).isTrue();
    }
}
