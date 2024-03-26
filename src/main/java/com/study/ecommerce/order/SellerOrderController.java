package com.study.ecommerce.order;

import com.study.ecommerce.auth.token.MemberDetails;
import com.study.ecommerce.order.dto.SellerOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/seller-order/{sellerOrderId}/ship")
    public SellerOrderResponse shipOrder(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable Long sellerOrderId
            ) {
        return sellerOrderService.shipOrder(member.getId(), sellerOrderId);
    }
}
