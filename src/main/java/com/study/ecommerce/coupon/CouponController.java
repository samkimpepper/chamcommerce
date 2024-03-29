package com.study.ecommerce.coupon;

import com.study.ecommerce.auth.token.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @GetMapping("/coupons/me")
    public ResponseEntity<List<MemberCouponResponse>> showMyCoupons(
            @AuthenticationPrincipal MemberDetails member
    ) {
        return ResponseEntity.ok(couponService.getMemberCoupons(member.getId()));
    }
}
