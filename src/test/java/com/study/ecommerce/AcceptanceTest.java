package com.study.ecommerce;

import com.study.ecommerce.member.MemberFixture;
import com.study.ecommerce.member.AuthService;
import com.study.ecommerce.member.acceptance.MemberSteps;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AcceptanceTest {
    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private AuthService authService;

    protected static String ACCESS_TOKEN_CUSTOMER;

    protected static String ACCESS_TOKEN_SELLER;

    protected static String ACCESS_TOKEN_SELLER2;

    protected static String ACCESS_TOKEN_DELIVERY_WORKER;

    protected static String ACCESS_TOKEN_ADMIN;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
        RestAssured.defaultParser = Parser.JSON;

        authService.signUp(MemberFixture.createCustomer());
        ACCESS_TOKEN_CUSTOMER = MemberSteps.requestToLogin(MemberFixture.CUSTOMER_EMAIL, MemberFixture.PASSWORD).getAccessToken();

        authService.signUp(MemberFixture.createSeller());
        ACCESS_TOKEN_SELLER = MemberSteps.requestToLogin(MemberFixture.SELLER_EMAIL, MemberFixture.PASSWORD).getAccessToken();

        authService.signUp(MemberFixture.createSeller2());
        ACCESS_TOKEN_SELLER2 = MemberSteps.requestToLogin(MemberFixture.SELLER2_EMAIL, MemberFixture.PASSWORD).getAccessToken();

        authService.signUp(MemberFixture.createDeliveryWorker());
        ACCESS_TOKEN_DELIVERY_WORKER = MemberSteps.requestToLogin(MemberFixture.DELIVERY_WORKER_EMAIL, MemberFixture.PASSWORD).getAccessToken();

        authService.signUp(MemberFixture.createAdmin());
        ACCESS_TOKEN_ADMIN = MemberSteps.requestToLogin(MemberFixture.ADMIN_EMAIL, MemberFixture.PASSWORD).getAccessToken();
    }
}
