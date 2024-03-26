package com.study.ecommerce.order;

import com.study.ecommerce.AcceptanceTest;
import com.study.ecommerce.member.MemberFixture;
import com.study.ecommerce.member.acceptance.MemberSteps;
import com.study.ecommerce.order.domain.OrderStatus;
import com.study.ecommerce.order.domain.SellerOrderStatus;
import com.study.ecommerce.order.dto.OrderResponse;
import com.study.ecommerce.order.dto.SellerOrderResponse;
import com.study.ecommerce.product.ProductFixture;
import com.study.ecommerce.product.ProductSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.study.ecommerce.order.DeliveryFeeCalculator.BASE_DELIVERY_FEE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderAcceptanceTest extends AcceptanceTest {

    private List<Long> productItemIds = new ArrayList<>();
    private List<Long> productItemIds2 = new ArrayList<>();

    private Long deliveryAddressId;
    @BeforeEach
    public void setUp() {
        super.setUp();
        productItemIds = ProductSteps.createProducts(ACCESS_TOKEN_SELLER);
        productItemIds2 = ProductSteps.createProducts(ACCESS_TOKEN_SELLER2);
        deliveryAddressId = MemberSteps.createDeliveryAddress(ACCESS_TOKEN_CUSTOMER, MemberFixture.createDefaultDeliveryAddress()).getId();
    }

    @Test
    public void createOrder() {

        // when
        OrderResponse response = OrderSteps.createOrder(Map.of(productItemIds.get(0), 1, productItemIds2.get(0), 1), deliveryAddressId, ACCESS_TOKEN_CUSTOMER);

        SellerOrderResponse sellerOrderResponse = OrderSteps.getSellerOrders(ACCESS_TOKEN_SELLER).get(0);
        SellerOrderResponse sellerOrderResponse2 = OrderSteps.getSellerOrders(ACCESS_TOKEN_SELLER2).get(0);

        // then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getTotalPrice()).isEqualTo(ProductFixture.PRICE * 2);
        assertThat(response.getDeliveryFee()).isEqualTo(BASE_DELIVERY_FEE * 2);
        assertThat(sellerOrderResponse.getTotalPrice()).isEqualTo(ProductFixture.PRICE);
        assertThat(sellerOrderResponse2.getTotalPrice()).isEqualTo(ProductFixture.PRICE);
    }

    @Test
    public void cancelOrder() {
        // given
        OrderResponse orderResponse = OrderSteps.createOrder(Map.of(productItemIds.get(0), 1, productItemIds2.get(0), 1), deliveryAddressId, ACCESS_TOKEN_CUSTOMER);

        // when
        OrderResponse response = OrderSteps.cancelOrder(orderResponse.getId(), ACCESS_TOKEN_CUSTOMER);

        SellerOrderResponse sellerOrderResponse = OrderSteps.getSellerOrders(ACCESS_TOKEN_SELLER).get(0);
        SellerOrderResponse sellerOrderResponse2 = OrderSteps.getSellerOrders(ACCESS_TOKEN_SELLER2).get(0);

        // then
        assertThat(response.getId()).isEqualTo(orderResponse.getId());
        assertThat(response.getStatus()).isEqualTo(OrderStatus.CANCELLED.toString());
        assertThat(sellerOrderResponse.getStatus()).isEqualTo(SellerOrderStatus.CANCELLED.toString());
    }

    @Test
    public void shipOrder() {
        // given
        OrderSteps.createOrder(Map.of(productItemIds.get(0), 1, productItemIds2.get(0), 1), deliveryAddressId, ACCESS_TOKEN_CUSTOMER);

        // when
        SellerOrderResponse sellerOrderResponse = OrderSteps.getSellerOrders(ACCESS_TOKEN_SELLER).get(0);
        SellerOrderResponse response = OrderSteps.shipOrder(sellerOrderResponse.getId(), ACCESS_TOKEN_SELLER);

        // then
        assertThat(response.getStatus()).isEqualTo(SellerOrderStatus.SHIPPED.toString());
    }
}
