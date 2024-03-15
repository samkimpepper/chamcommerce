package com.study.ecommerce.product;

import com.study.ecommerce.product.dto.ProductResponse;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

import java.util.List;

public class ProductSteps {
    public static ProductResponse createProductInfo(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(ProductFixture.defaultProductInfoCreateRequest())
                .auth().oauth2(accessToken)
                .when().post("/products/info")
                .then().log().all().extract().as(ProductResponse.class);
    }

    public static ProductResponse createProductOptionGroup(Long productId, List<Long> detailIds, String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(ProductFixture.defaultOptionGroupCreateRequest(detailIds))
                .auth().oauth2(accessToken)
                .when().post("/products/{productId}/option-groups", productId)
                .then().log().all().extract().as(ProductResponse.class);
    }

    public static Long parseProductId(ProductResponse productResponse) {
        return productResponse.getId();
    }

    public static Long parseProductOptionDetailId(ProductResponse productResponse, String optionName, String optionValue) {
        return productResponse.getOptions().stream()
                .filter(optionResponse -> optionResponse.getName().equals(optionName))
                .flatMap(optionResponse -> optionResponse.getDetails().stream())
                .filter(detailResponse -> detailResponse.getValue().equals(optionValue))
                .findFirst()
                .map(optionResponse -> optionResponse.getId())
                .orElseThrow(() -> new IllegalArgumentException("옵션 정보가 존재하지 않습니다."));
    }
}
