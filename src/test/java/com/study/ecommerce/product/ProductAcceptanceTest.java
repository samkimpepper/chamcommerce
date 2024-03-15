package com.study.ecommerce.product;

import com.study.ecommerce.member.AcceptanceTest;
import com.study.ecommerce.product.dto.ProductResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductAcceptanceTest extends AcceptanceTest {
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void createProductInfo() {
        ProductResponse response = ProductSteps.createProductInfo(ACCESS_TOKEN_SELLER);

        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void createProductOptionGroup() {
        ProductResponse productResponse = ProductSteps.createProductInfo(ACCESS_TOKEN_SELLER);

        Long productId = ProductSteps.parseProductId(productResponse);
        Long detailId = ProductSteps.parseProductOptionDetailId(productResponse, "색상", "검정색");
        Long detailId2 = ProductSteps.parseProductOptionDetailId(productResponse, "사이즈", "L");

        ProductResponse response = ProductSteps.createProductOptionGroup(productId, List.of(detailId, detailId2), ACCESS_TOKEN_SELLER);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getOptionGroups().get(0).getPrice()).isEqualTo(ProductFixture.PRICE);
        assertThat(response.getTotalStock()).isEqualTo(ProductFixture.STOCK);
    }

    @Test
    public void createProductOptionGroups() {
        ProductResponse productResponse = ProductSteps.createProductInfo(ACCESS_TOKEN_SELLER);

        Long productId = ProductSteps.parseProductId(productResponse);
        Long detailId = ProductSteps.parseProductOptionDetailId(productResponse, "색상", "검정색");
        Long detailId2 = ProductSteps.parseProductOptionDetailId(productResponse, "사이즈", "L");

        ProductResponse response = ProductSteps.createProductOptionGroup(productId, List.of(detailId, detailId2), ACCESS_TOKEN_SELLER);

        detailId = ProductSteps.parseProductOptionDetailId(productResponse, "색상", "흰색");
        detailId2 = ProductSteps.parseProductOptionDetailId(productResponse, "사이즈", "M");

        response = ProductSteps.createProductOptionGroup(productId, List.of(detailId, detailId2), ACCESS_TOKEN_SELLER);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getOptionGroups().get(0).getPrice()).isEqualTo(ProductFixture.PRICE);
        assertThat(response.getTotalStock()).isEqualTo(ProductFixture.STOCK * 2);
    }

}
