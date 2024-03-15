package com.study.ecommerce;

import com.study.ecommerce.DatabaseCleanup;
import com.study.ecommerce.member.MemberFixture;
import com.study.ecommerce.member.MemberService;
import com.study.ecommerce.member.acceptance.MemberSteps;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AcceptanceTest {
    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private MemberService memberService;

    protected static String ACCESS_TOKEN_CUSTOMER;

    protected static String ACCESS_TOKEN_SELLER;

    @BeforeEach
    public void setUp() {
        //databaseCleanup.execute();
        RestAssured.defaultParser = Parser.JSON;

        memberService.signUp(MemberFixture.createCustomer());
        ACCESS_TOKEN_CUSTOMER = MemberSteps.requestToLogin(MemberFixture.CUSTOMER_EMAIL, MemberFixture.PASSWORD).getAccessToken();

        memberService.signUp(MemberFixture.createSeller());
        ACCESS_TOKEN_SELLER = MemberSteps.requestToLogin(MemberFixture.SELLER_EMAIL, MemberFixture.PASSWORD).getAccessToken();
    }
}
