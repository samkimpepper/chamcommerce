package com.study.ecommerce.product;

import com.study.ecommerce.product.dto.ProductResponse;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

public class ProductSteps {
    public static List<Long> createProducts(String accessToken) {
        List<Long> productItemIds = new ArrayList<>();

        ProductResponse productResponse = ProductSteps.createProductInfo(accessToken);

        Long productId = ProductSteps.parseProductId(productResponse);
        Long detailId = ProductSteps.parseProductOptionDetailId(productResponse, "색상", "검정색");
        Long detailId2 = ProductSteps.parseProductOptionDetailId(productResponse, "사이즈", "L");

        ProductResponse response = ProductSteps.createProductOptionGroup(productId, List.of(detailId, detailId2), accessToken);

        productItemIds.add(ProductSteps.parseProductOptionGroupId(response, 0));

        detailId = ProductSteps.parseProductOptionDetailId(productResponse, "색상", "흰색");
        detailId2 = ProductSteps.parseProductOptionDetailId(productResponse, "사이즈", "M");

        response = ProductSteps.createProductOptionGroup(productId, List.of(detailId, detailId2), accessToken);

        productItemIds.add(ProductSteps.parseProductOptionGroupId(response, 1));

        return productItemIds;
    }
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

    public static Long parseProductOptionGroupId(ProductResponse productResponse, int index) {
        return productResponse.getOptionGroups().get(index).getId();
    }
}
