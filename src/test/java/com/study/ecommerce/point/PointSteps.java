package com.study.ecommerce.point;

import io.restassured.RestAssured;

import java.util.List;

public class PointSteps {

    public static List<PointHistoryResponse> showPointHistories(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/point-histories")
                .then().log().all().extract().jsonPath().getList(".", PointHistoryResponse.class);
    }
}
