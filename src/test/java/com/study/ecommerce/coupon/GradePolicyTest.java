package com.study.ecommerce.coupon;

import com.study.ecommerce.DatabaseCleanup;
import com.study.ecommerce.delivery.DeliveryResponse;
import com.study.ecommerce.delivery.DeliveryService;
import com.study.ecommerce.member.*;
import com.study.ecommerce.member.domain.Member;
import com.study.ecommerce.member.domain.MemberGrade;
import com.study.ecommerce.member.dto.DeliveryAddressResponse;
import com.study.ecommerce.order.OrderService;
import com.study.ecommerce.order.SellerOrderService;
import com.study.ecommerce.order.dto.OrderCreateRequest;
import com.study.ecommerce.order.dto.OrderItemRequest;
import com.study.ecommerce.order.dto.OrderResponse;
import com.study.ecommerce.order.dto.SellerOrderResponse;
import com.study.ecommerce.product.ProductFixture;
import com.study.ecommerce.product.ProductSteps;
import com.study.ecommerce.product.dto.ProductResponse;
import com.study.ecommerce.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("test")
@SpringBootTest
public class GradePolicyTest {
    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private GradePolicy gradePolicy;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SellerOrderService sellerOrderService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private CouponService couponService;

    Member customer;

    Member seller;

    Member deliveryWorker;

    Long productItemId;

    DeliveryAddressResponse deliveryAddress;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();

        customer = authService.signUp(MemberFixture.createCustomer());
        seller = authService.signUp(MemberFixture.createSeller());
        Member seller2 = authService.signUp(MemberFixture.createSeller2());
        deliveryWorker = authService.signUp(MemberFixture.createDeliveryWorker());

        deliveryAddress = deliveryAddressService.createDeliveryAddress(customer.getId(), MemberFixture.createDefaultDeliveryAddress());

        // 판매자가 상품을 등록한다.
        ProductResponse product = productService.createProductInfo(ProductFixture.defaultProductInfoCreateRequest(), seller.getId());
        Long detailId = ProductSteps.parseProductOptionDetailId(product, "색상", "검정색");
        Long detailId2 = ProductSteps.parseProductOptionDetailId(product, "사이즈", "L");
        product = productService.createProductItem(product.getId(), ProductFixture.defaultProductItemCreateRequest(List.of(detailId, detailId2)));
        productItemId = ProductSteps.parseProductItemId(product, 0);
    }

    private void completeOrderFlow(int quantity) {
        // 고객이 상품을 주문한다
        OrderResponse order = orderService.createOrder(new OrderCreateRequest(
                List.of(new OrderItemRequest(productItemId, quantity)),
                deliveryAddress.getId(),
                "INSTANT",
                0), customer.getId());

        // 판매자가 상품을 출고한다
        SellerOrderResponse sellerOrder = sellerOrderService.getAllSellerOrders(seller.getId()).get(0);
        sellerOrderService.shipOrder(seller.getId(), sellerOrder.getId());

        // 배송원이 배송을 완료한다
        DeliveryResponse delivery = deliveryService.getAllDelivery(deliveryWorker.getId()).get(0);
        deliveryService.deliver(delivery.getId());

        // 고객이 구매확정을 한다
        orderService.completeOrder(order.getId());
    }

    @Test
    public void testApplyRedGrade() {
        // given
        completeOrderFlow(10);

        // when
        gradePolicy.applyGrade(customer);

        // then
        MemberResponse customerInfo = memberService.getMemberInfo(customer.getId());
        assertThat(customerInfo.getGrade()).isEqualTo(MemberGrade.RED.name());
    }

    @Test
    public void testApplyRedGradeCoupons() {
        // given
        completeOrderFlow(10);

        // when
        gradePolicy.applyGrade(customer);
        gradePolicy.applyGradeCoupon(customer);

        // then
        List<MemberCouponResponse> coupons = couponService.getMemberCoupons(customer.getId());
        assertThat(coupons.size()).isEqualTo(2);
    }
}
