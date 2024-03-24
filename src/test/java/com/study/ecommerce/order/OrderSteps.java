package com.study.ecommerce.order;

import com.study.ecommerce.order.dto.OrderCreateRequest;
import com.study.ecommerce.order.dto.OrderItemRequest;
import com.study.ecommerce.order.dto.OrderResponse;
import com.study.ecommerce.order.dto.SellerOrderResponse;
import io.restassured.RestAssured;

import java.util.List;
import java.util.Map;

public class OrderSteps {
    public static OrderResponse createOrder(Map<Long, Integer> productOptionGroupQuantityMap, Long deliveryAddressId, String accessToken) {
        List<OrderItemRequest> orderOptionGroups = List.of(
                productOptionGroupQuantityMap.entrySet().stream()
                        .map(entry -> new OrderItemRequest(entry.getKey(), entry.getValue()))
                        .toArray(OrderItemRequest[]::new)
        );

        return RestAssured
                .given().log().all()
                .contentType("application/json")
                .body(new OrderCreateRequest(orderOptionGroups, deliveryAddressId, "INSTANT"))
                .auth().oauth2(accessToken)
                .when().post("/orders")
                .then().log().all().extract().as(OrderResponse.class);
    }

    public static OrderResponse cancelOrder(Long orderId, String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().post("/orders/" + orderId + "/cancel")
                .then().log().all().extract().as(OrderResponse.class);
    }

    public static OrderResponse showOrderDetail(Long orderId, String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/orders/" + orderId)
                .then().log().all().extract().as(OrderResponse.class);
    }

    public static List<SellerOrderResponse> getSellerOrders(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/seller-order/all")
                .then().log().all().extract().jsonPath().getList(".", SellerOrderResponse.class);
    }



}
