package com.study.ecommerce.product;

import com.study.ecommerce.product.dto.ProductResponse;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

import java.util.List;

public class ProductSteps {
    public static ProductResponse createProductInfo() {

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(ProductFixture.defaultProductInfoCreateRequest())
                .when().post("/products/info")
                .then().log().all().extract().as(ProductResponse.class);
    }
}
