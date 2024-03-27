package com.study.ecommerce;

import com.study.ecommerce.delivery.DeliverySteps;
import com.study.ecommerce.member.MemberFixture;
import com.study.ecommerce.member.acceptance.MemberSteps;
import com.study.ecommerce.order.OrderSteps;
import com.study.ecommerce.order.domain.OrderStatus;
import com.study.ecommerce.order.dto.OrderResponse;
import com.study.ecommerce.point.PointHistoryResponse;
import com.study.ecommerce.point.PointSteps;
import com.study.ecommerce.product.ProductSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderFlowAcceptanceTest extends AcceptanceTest {
    List<Long> productItemIds;
    List<Long> productItemIds2;
    Long deliveryAddressId;
    @BeforeEach
    public void setUp() {
        super.setUp();

        productItemIds = ProductSteps.createProducts(ACCESS_TOKEN_SELLER);
        productItemIds2 = ProductSteps.createProducts(ACCESS_TOKEN_SELLER2);
        deliveryAddressId = MemberSteps.createDeliveryAddress(ACCESS_TOKEN_CUSTOMER, MemberFixture.createDefaultDeliveryAddress()).getId();
    }

    @Test
    public void testOrderFlow() {
        // when
        Long orderId = OrderSteps.createOrder(Map.of(productItemIds.get(0), 1, productItemIds2.get(0), 1), deliveryAddressId, ACCESS_TOKEN_CUSTOMER).getId();

        Long sellerOrderId = OrderSteps.getSellerOrders(ACCESS_TOKEN_SELLER).get(0).getId();
        Long sellerOrderId2 = OrderSteps.getSellerOrders(ACCESS_TOKEN_SELLER2).get(0).getId();

        OrderSteps.shipOrder(sellerOrderId, ACCESS_TOKEN_SELLER);
        OrderSteps.shipOrder(sellerOrderId2, ACCESS_TOKEN_SELLER2);

        DeliverySteps.startDelivery(DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(0).getId(), ACCESS_TOKEN_DELIVERY_WORKER);
        DeliverySteps.deliverDelivery(DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(0).getId(), ACCESS_TOKEN_DELIVERY_WORKER);
        DeliverySteps.startDelivery(DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(1).getId(), ACCESS_TOKEN_DELIVERY_WORKER);
        DeliverySteps.deliverDelivery(DeliverySteps.showAllDelivery(ACCESS_TOKEN_DELIVERY_WORKER).get(1).getId(), ACCESS_TOKEN_DELIVERY_WORKER);

        OrderResponse order = OrderSteps.completeOrder(orderId, ACCESS_TOKEN_CUSTOMER);
        PointHistoryResponse pointHistory = PointSteps.showPointHistories(ACCESS_TOKEN_CUSTOMER).get(0);

        // then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED.name());
        assertThat(pointHistory.getAmount()).isNotZero();
    }
}
