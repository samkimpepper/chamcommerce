package com.study.ecommerce.point;

import com.study.ecommerce.point.pointevent.dto.PointEventCreateRequest;
import com.study.ecommerce.point.pointevent.dto.PointEventResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

public class PointEventSteps {
    public static PointEventResponse createPointEvent(PointEventCreateRequest request, String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType("application/json")
                .body(request)
                .auth().oauth2(accessToken)
                .when().post("/point-events")
                .then().log().all().extract().as(PointEventResponse.class);
    }

    public static List<PointEventResponse> showOngoingPointEvents(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/point-events/ongoing")
                .then().log().all().extract().jsonPath().getList(".", PointEventResponse.class);
    }

    public static ExtractableResponse<Response> participate(Long eventId, String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().post("/events/" + eventId + "/participate")
                .then().log().all().extract();
    }
}
