package com.study.ecommerce.order;

import com.study.ecommerce.auth.token.MemberDetails;
import com.study.ecommerce.order.dto.SellerOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SellerOrderController {
    private final SellerOrderService sellerOrderService;

    @GetMapping("/seller-order/all")
    public List<SellerOrderResponse> showAllSellerOrders(
            @AuthenticationPrincipal MemberDetails member
            ) {
        return sellerOrderService.getAllSellerOrders(member.getId());
    }
}
