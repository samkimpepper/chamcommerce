package com.study.ecommerce.member.acceptance;

import com.study.ecommerce.member.dto.LoginRequest;
import com.study.ecommerce.member.dto.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class MemberSteps {
    public static TokenResponse requestToLogin(String email, String password) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new LoginRequest(email, password))
                .when().post("/member/login")
                .then().log().all().extract().as(TokenResponse.class);
    }
}
