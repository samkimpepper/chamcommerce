package com.study.ecommerce.order.units;

import com.study.ecommerce.member.Member;
import com.study.ecommerce.member.MemberFixture;
import com.study.ecommerce.member.MemberService;
import com.study.ecommerce.order.OrderService;
import com.study.ecommerce.order.domain.OrderStatus;
import com.study.ecommerce.order.dto.OrderCreateRequest;
import com.study.ecommerce.order.dto.OrderItemRequest;
import com.study.ecommerce.order.dto.OrderResponse;
import com.study.ecommerce.product.ProductFixture;
import com.study.ecommerce.product.dto.ProductResponse;
import com.study.ecommerce.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MemberService memberService;

    Member customer;
    Member seller;
    ProductResponse product;
    int stock;

    @BeforeEach
    public void setUp() {
        customer = memberService.signUp(MemberFixture.createCustomer());
        seller = memberService.signUp(MemberFixture.createSeller());

        product = productService.createProductInfo(ProductFixture.defaultProductInfoCreateRequest(), seller.getId());
        product = productService.createProductItem(
                product.getId(),
                ProductFixture.defaultProductItemCreateRequest(
                        List.of(product.getOptions().get(0).getDetails().get(0).getId())));
        stock = product.getTotalStock();
    }

    @Test
    public void createOrder_shouldDecreaseProductStock() {
        // given
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                List.of(new OrderItemRequest(product.getOptionGroups().get(0).getId(), 1)),
                "서울시 강남구",
                "INSTANT");

        // when
        OrderResponse order = orderService.createOrder(orderCreateRequest, customer.getId());
        product = productService.getProduct(product.getId());

        // then
        assertThat(order.getId()).isNotNull();
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDERED.name());
        assertThat(product.getTotalStock()).isEqualTo(stock - 1);
    }

    @Test
    public void cancelOrder_shouldIncreaseProductStock() {
        // given
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                List.of(new OrderItemRequest(product.getOptionGroups().get(0).getId(), 1)),
                "서울시 강남구",
                "INSTANT");
        OrderResponse order = orderService.createOrder(orderCreateRequest, customer.getId());
        product = productService.getProduct(product.getId());
        stock = product.getTotalStock();

        // when
        order = orderService.cancelOrder(order.getId());
        product = productService.getProduct(product.getId());

        // then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED.name());
        assertThat(product.getTotalStock()).isEqualTo(stock + 1);
    }
}
