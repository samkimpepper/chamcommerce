package com.study.ecommerce.member.acceptance;

import com.study.ecommerce.member.MemberResponse;
import com.study.ecommerce.member.dto.DeliveryAddressCreateRequest;
import com.study.ecommerce.member.dto.DeliveryAddressResponse;
import com.study.ecommerce.member.dto.LoginRequest;
import com.study.ecommerce.member.dto.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.List;

public class MemberSteps {
    public static TokenResponse requestToLogin(String email, String password) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new LoginRequest(email, password))
                .when().post("/member/login")
                .then().log().all().extract().as(TokenResponse.class);
    }

    public static MemberResponse showMemberInfo(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/member/me")
                .then().log().all().extract().as(MemberResponse.class);
    }

    public static DeliveryAddressResponse createDeliveryAddress(String accessToken, DeliveryAddressCreateRequest request) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/member/update/delivery-address")
                .then().log().all().extract().as(DeliveryAddressResponse.class);
    }

    public static List<DeliveryAddressResponse> getDeliveryAddresses(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/member/delivery-addresses")
                .then().log().all().extract().jsonPath().getList(".", DeliveryAddressResponse.class);
    }

    public static DeliveryAddressResponse findDeliveryAddressById(List<DeliveryAddressResponse> deliveryAddresses, Long deliveryAddressId) {
        return deliveryAddresses.stream()
                .filter(deliveryAddress -> deliveryAddress.getId().equals(deliveryAddressId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 배송지가 없습니다."));
    }
}
