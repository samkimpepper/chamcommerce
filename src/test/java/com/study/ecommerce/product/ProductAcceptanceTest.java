package com.study.ecommerce.product;

import com.study.ecommerce.AcceptanceTest;
import com.study.ecommerce.product.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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

        ProductResponse response = ProductSteps.createProductItems(productId, List.of(detailId, detailId2), ACCESS_TOKEN_SELLER);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getOptionGroups().get(0).getPrice()).isEqualTo(ProductFixture.PRICE);
        assertThat(response.getTotalStock()).isEqualTo(ProductFixture.STOCK);
    }

    @Test
    public void createProductOptionGroups() {
        // given
        ProductResponse productResponse = ProductSteps.createProductInfo(ACCESS_TOKEN_SELLER);

        Long productId = ProductSteps.parseProductId(productResponse);
        Long detailId = ProductSteps.parseProductOptionDetailId(productResponse, "색상", "검정색");
        Long detailId2 = ProductSteps.parseProductOptionDetailId(productResponse, "사이즈", "L");

        ProductResponse response = ProductSteps.createProductItems(productId, List.of(detailId, detailId2), ACCESS_TOKEN_SELLER);

        detailId = ProductSteps.parseProductOptionDetailId(productResponse, "색상", "흰색");
        detailId2 = ProductSteps.parseProductOptionDetailId(productResponse, "사이즈", "M");

        response = ProductSteps.createProductItems(productId, List.of(detailId, detailId2), ACCESS_TOKEN_SELLER);

        // then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getOptionGroups().get(0).getPrice()).isEqualTo(ProductFixture.PRICE);
        assertThat(response.getTotalStock()).isEqualTo(ProductFixture.STOCK * 2);
    }

    @Test
    public void restockProduct() {
        // given
        ProductResponse productResponse = ProductSteps.createProductInfo(ACCESS_TOKEN_SELLER);

        Long productId = ProductSteps.parseProductId(productResponse);
        Long detailId = ProductSteps.parseProductOptionDetailId(productResponse, "색상", "검정색");
        Long detailId2 = ProductSteps.parseProductOptionDetailId(productResponse, "사이즈", "L");

        ProductResponse response = ProductSteps.createProductItems(productId, List.of(detailId, detailId2), ACCESS_TOKEN_SELLER);

        Long productItemId = ProductSteps.parseProductItemId(response, 0);

        // when
        response = ProductSteps.restockProduct(productId, Map.of(productItemId, 10), ACCESS_TOKEN_SELLER);

        // then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getOptionGroups().get(0).getPrice()).isEqualTo(ProductFixture.PRICE);
        assertThat(response.getTotalStock()).isEqualTo(ProductFixture.STOCK + 10);
    }

}
