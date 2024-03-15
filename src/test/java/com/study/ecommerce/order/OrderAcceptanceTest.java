package com.study.ecommerce.order;

import com.study.ecommerce.AcceptanceTest;
import com.study.ecommerce.order.dto.OrderResponse;
import com.study.ecommerce.product.ProductFixture;
import com.study.ecommerce.product.ProductSteps;
import com.study.ecommerce.product.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.study.ecommerce.order.DeliveryFeeCalculator.BASE_DELIVERY_FEE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderAcceptanceTest extends AcceptanceTest {

    private List<Long> productOptionGroupIds = new ArrayList<>();
    @BeforeEach
    public void setUp() {
        super.setUp();
        ProductResponse productResponse = ProductSteps.createProductInfo(ACCESS_TOKEN_SELLER);

        Long productId = ProductSteps.parseProductId(productResponse);
        Long detailId = ProductSteps.parseProductOptionDetailId(productResponse, "색상", "검정색");
        Long detailId2 = ProductSteps.parseProductOptionDetailId(productResponse, "사이즈", "L");

        ProductResponse response = ProductSteps.createProductOptionGroup(productId, List.of(detailId, detailId2), ACCESS_TOKEN_SELLER);

        productOptionGroupIds.add(ProductSteps.parseProductOptionGroupId(response, 0));

        detailId = ProductSteps.parseProductOptionDetailId(productResponse, "색상", "흰색");
        detailId2 = ProductSteps.parseProductOptionDetailId(productResponse, "사이즈", "M");

        response = ProductSteps.createProductOptionGroup(productId, List.of(detailId, detailId2), ACCESS_TOKEN_SELLER);

        productOptionGroupIds.add(ProductSteps.parseProductOptionGroupId(response, 1));
    }

    @Test
    public void createOrder() {

        // when
        OrderResponse response = OrderSteps.createOrder(Map.of(productOptionGroupIds.get(0), 2), ACCESS_TOKEN_CUSTOMER);

        // then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getTotalPrice()).isEqualTo(ProductFixture.PRICE * 2);
        assertThat(response.getDeliveryFee()).isEqualTo(0);
    }
}
