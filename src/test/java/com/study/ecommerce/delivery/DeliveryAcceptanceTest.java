package com.study.ecommerce.delivery;

import com.study.ecommerce.AcceptanceTest;
import com.study.ecommerce.delivery.domain.DeliveryStatus;
import com.study.ecommerce.member.MemberFixture;
import com.study.ecommerce.member.acceptance.MemberSteps;
import com.study.ecommerce.order.OrderSteps;
import com.study.ecommerce.order.domain.OrderStatus;
import com.study.ecommerce.order.dto.OrderResponse;
import com.study.ecommerce.product.ProductSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeliveryAcceptanceTest extends AcceptanceTest {
    Long orderId;
    Long sellerOrderId;
    Long sellerOrderId2;
    @BeforeEach
    public void setUp() {
        super.setUp();

        List<Long> productItemIds = ProductSteps.createProducts(ACCESS_TOKEN_SELLER);
        List<Long> productItemIds2 = ProductSteps.createProducts(ACCESS_TOKEN_SELLER2);
        Long deliveryAddressId = MemberSteps.createDeliveryAddress(ACCESS_TOKEN_CUSTOMER, MemberFixture.createDefaultDeliveryAddress()).getId();
        orderId = OrderSteps.createOrder(Map.of(productItemIds.get(0), 1, productItemIds2.get(0), 1), deliveryAddressId, ACCESS_TOKEN_CUSTOMER).getId();
        sellerOrderId = OrderSteps.getSellerOrders(ACCESS_TOKEN_SELLER).get(0).getId();
        sellerOrderId2 = OrderSteps.getSellerOrders(ACCESS_TOKEN_SELLER2).get(0).getId();

    }

    @Test
    public void shipDeliverySuccess() {
        // given
        OrderSteps.shipOrder(sellerOrderId, ACCESS_TOKEN_SELLER);

        // when
        DeliveryResponse response = DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(0);

        // then
        assertThat(response.getOrderId()).isEqualTo(orderId);
        assertThat(response.getStatus()).isEqualTo(DeliveryStatus.SHIPPED.name());
    }

    @Test
    public void startDeliverySuccess() {
        // given
        OrderSteps.shipOrder(sellerOrderId, ACCESS_TOKEN_SELLER);

        // when
        DeliveryResponse response = DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(0);
        DeliverySteps.startDelivery(response.getId(), ACCESS_TOKEN_DELIVERY_WORKER);
        OrderResponse order = OrderSteps.showOrderDetail(orderId, ACCESS_TOKEN_CUSTOMER);

        // then
        response = DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(0);
        assertThat(response.getStatus()).isEqualTo(DeliveryStatus.DELIVERING.name());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.DELIVERING.name());
    }

    @Test
    public void singleDeliverySuccess() {
        // given
        OrderSteps.shipOrder(sellerOrderId, ACCESS_TOKEN_SELLER);

        // when
        DeliverySteps.startDelivery(DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(0).getId(), ACCESS_TOKEN_DELIVERY_WORKER);
        DeliverySteps.deliverDelivery(DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(0).getId(), ACCESS_TOKEN_DELIVERY_WORKER);
        OrderResponse order = OrderSteps.showOrderDetail(orderId, ACCESS_TOKEN_CUSTOMER);
        DeliveryResponse response = DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(0);

        // then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.DELIVERING.name());
        assertThat(response.getStatus()).isEqualTo(DeliveryStatus.DELIVERED.name());
    }

    @Test
    public void allDeliverySuccess() {
        // given
        OrderSteps.shipOrder(sellerOrderId, ACCESS_TOKEN_SELLER);
        OrderSteps.shipOrder(sellerOrderId2, ACCESS_TOKEN_SELLER2);

        // when
        DeliverySteps.startDelivery(DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(0).getId(), ACCESS_TOKEN_DELIVERY_WORKER);
        DeliverySteps.deliverDelivery(DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(0).getId(), ACCESS_TOKEN_DELIVERY_WORKER);
        DeliverySteps.startDelivery(DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(1).getId(), ACCESS_TOKEN_DELIVERY_WORKER);
        DeliverySteps.deliverDelivery(DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(1).getId(), ACCESS_TOKEN_DELIVERY_WORKER);
        OrderResponse order = OrderSteps.showOrderDetail(orderId, ACCESS_TOKEN_CUSTOMER);
        List<DeliveryResponse> responses = DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER);

        // then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.DELIVERED.name());
        assertThat(responses.get(0).getStatus()).isEqualTo(DeliveryStatus.DELIVERED.name());
        assertThat(responses.get(1).getStatus()).isEqualTo(DeliveryStatus.DELIVERED.name());
    }
}
