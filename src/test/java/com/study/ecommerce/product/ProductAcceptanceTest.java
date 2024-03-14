package com.study.ecommerce.product;

import com.study.ecommerce.member.AcceptanceTest;
import com.study.ecommerce.product.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductAcceptanceTest extends AcceptanceTest {
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void createProductInfo() {
        ProductResponse response = ProductSteps.createProductInfo();

        assertThat(response.getId()).isNotNull();
    }
}
