package com.study.ecommerce.delivery;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

public class DeliverySteps {

    public static ExtractableResponse<Response> startDelivery(Long deliveryId, String accessToken) {
        return RestAssured
                .given().log().all()
                .when()
                .auth().oauth2(accessToken)
                .put("/deliveries/{deliveryId}/start", deliveryId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> deliverDelivery(Long deliveryId, String accessToken) {
        return RestAssured
                .given().log().all()
                .when()
                .auth().oauth2(accessToken)
                .put("/deliveries/{deliveryId}/deliver", deliveryId)
                .then().log().all()
                .extract();
    }

    public static List<DeliveryResponse> showAllDelivery(String accessToken) {
        return RestAssured
                .given().log().all()
                .when()
                .auth().oauth2(accessToken)
                .get("/deliveries/all")
                .then().log().all()
                .extract().jsonPath().getList(".", DeliveryResponse.class);
    }
}
