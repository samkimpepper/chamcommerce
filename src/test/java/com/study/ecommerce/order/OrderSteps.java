package com.study.ecommerce.order;

import com.study.ecommerce.order.dto.OrderCreateRequest;
import com.study.ecommerce.order.dto.OrderItemRequest;
import com.study.ecommerce.order.dto.OrderResponse;
import io.restassured.RestAssured;

import java.util.List;
import java.util.Map;

public class OrderSteps {
    public static OrderResponse createOrder(Map<Long, Integer> productOptionGroupQuantityMap, String accessToken) {
        List<OrderItemRequest> orderOptionGroups = List.of(
                productOptionGroupQuantityMap.entrySet().stream()
                        .map(entry -> new OrderItemRequest(entry.getKey(), entry.getValue()))
                        .toArray(OrderItemRequest[]::new)
        );

        return RestAssured
                .given().log().all()
                .contentType("application/json")
                .body(new OrderCreateRequest(orderOptionGroups, "서울시 강남구", "INSTANT"))
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

}
