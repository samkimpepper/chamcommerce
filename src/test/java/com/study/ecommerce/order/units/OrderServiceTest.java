package com.study.ecommerce.order.units;

import com.study.ecommerce.member.DeliveryAddressService;
import com.study.ecommerce.member.Member;
import com.study.ecommerce.member.MemberFixture;
import com.study.ecommerce.member.AuthService;
import com.study.ecommerce.member.dto.DeliveryAddressResponse;
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
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private AuthService authService;

    Member customer;
    Member seller;
    ProductResponse product;
    DeliveryAddressResponse deliveryAddress;
    int stock;

    @BeforeEach
    public void setUp() {
        customer = authService.signUp(MemberFixture.createCustomer());
        seller = authService.signUp(MemberFixture.createSeller());

        product = productService.createProductInfo(ProductFixture.defaultProductInfoCreateRequest(), seller.getId());
        product = productService.createProductItem(
                product.getId(),
                ProductFixture.defaultProductItemCreateRequest(
                        List.of(product.getOptions().get(0).getDetails().get(0).getId())));
        stock = product.getTotalStock();
        deliveryAddress = deliveryAddressService.createDeliveryAddress(customer.getId(), MemberFixture.createDefaultDeliveryAddress());
    }

    @Test
    public void createOrder_shouldDecreaseProductStock() {
        // given
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                List.of(new OrderItemRequest(product.getOptionGroups().get(0).getId(), 1)),
                deliveryAddress.getId(),
                "INSTANT",
                0);

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
                deliveryAddress.getId(),
                "INSTANT",
                0);
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
