package com.study.ecommerce.order.units;

import com.study.ecommerce.member.*;
import com.study.ecommerce.member.domain.Member;
import com.study.ecommerce.member.dto.DeliveryAddressResponse;
import com.study.ecommerce.order.repository.OrderRepository;
import com.study.ecommerce.order.OrderService;
import com.study.ecommerce.order.domain.Order;
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

import static com.study.ecommerce.order.DeliveryFeeCalculator.BASE_DELIVERY_FEE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class DeliveryFeeCalculatorTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthService authService;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private OrderRepository orderRepository;

    Member customer;
    Member seller;
    Member seller2;
    ProductResponse product;
    ProductResponse product2;
    DeliveryAddressResponse deliveryAddress;

    @BeforeEach
    public void setUp() {
        customer = authService.signUp(MemberFixture.createCustomer());
        seller = authService.signUp(MemberFixture.createSeller());
        seller2 = authService.signUp(MemberFixture.createSeller2());

        product = productService.createProductInfo(ProductFixture.defaultProductInfoCreateRequest(), seller.getId());
        product = productService.createProductItem(
                product.getId(),
                ProductFixture.defaultProductItemCreateRequest(
                        List.of(product.getOptions().get(0).getDetails().get(0).getId())));

        product2 = productService.createProductInfo(ProductFixture.defaultProductInfoCreateRequest(), seller2.getId());
        product2 = productService.createProductItem(
                product2.getId(),
                ProductFixture.defaultProductItemCreateRequest(
                        List.of(product2.getOptions().get(0).getDetails().get(0).getId())));

        deliveryAddress = deliveryAddressService.createDeliveryAddress(customer.getId(), MemberFixture.createDefaultDeliveryAddress());
    }

    @Test
    public void multipleDeliveryFeesForDifferentSellers() {
        // given
        OrderResponse orderResponse = orderService.createOrder(
                new OrderCreateRequest(
                        List.of(new OrderItemRequest(product.getOptionGroups().get(0).getId(), 1),
                                new OrderItemRequest(product2.getOptionGroups().get(0).getId(), 1)),
                        deliveryAddress.getId(),
                        "INSTANT",
                        0),
                customer.getId());

        // when
        Order order = orderRepository.findById(orderResponse.getId()).get();
        int deliveryFee = order.getDeliveryFee();

        // then
        assertThat(deliveryFee).isEqualTo(BASE_DELIVERY_FEE * 2);
    }

    @Test
    public void singleDeliveryFeeForSameSeller() {
        // given
        OrderResponse orderResponse = orderService.createOrder(
                new OrderCreateRequest(
                        List.of(new OrderItemRequest(product.getOptionGroups().get(0).getId(), 1),
                                new OrderItemRequest(product.getOptionGroups().get(0).getId(), 1)),
                        deliveryAddress.getId(),
                        "INSTANT",
                        0),
                customer.getId());

        // when
        Order order = orderRepository.findById(orderResponse.getId()).get();
        int deliveryFee = order.getDeliveryFee();

        // then
        assertThat(deliveryFee).isEqualTo(BASE_DELIVERY_FEE);
    }

    @Test
    public void zeroDeliveryFeeForOver50000() {
        // given
        OrderResponse orderResponse = orderService.createOrder(
                new OrderCreateRequest(
                        List.of(new OrderItemRequest(product.getOptionGroups().get(0).getId(), 4)),
                        deliveryAddress.getId(),
                        "INSTANT"
                                ,0),
                customer.getId());

        // when
        Order order = orderRepository.findById(orderResponse.getId()).get();
        int deliveryFee = order.getDeliveryFee();

        // then
        assertThat(deliveryFee).isEqualTo(0);
    }
}
