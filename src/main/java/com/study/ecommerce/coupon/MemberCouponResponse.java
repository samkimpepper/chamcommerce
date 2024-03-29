package com.study.ecommerce.coupon;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberCouponResponse {
    private String name;
    private String description;
    private int discountAmount;
    private int minimumOrderAmount;
    private int discountRate;
    private String type;
    private String issuedAt;
    private String expiredAt;

    public static MemberCouponResponse from(MemberCoupon coupon) {
        return MemberCouponResponse.builder()
                .name(coupon.getCoupon().getName())
                .description(coupon.getCoupon().getDescription())
                .discountAmount(coupon.getCoupon().getDiscountAmount())
                .minimumOrderAmount(coupon.getCoupon().getMinimumOrderAmount())
                .discountRate(coupon.getCoupon().getDiscountRate())
                .type(coupon.getCoupon().getType().name())
                .issuedAt(coupon.getIssuedAt().toString())
                .expiredAt(coupon.getExpiredAt().toString())
                .build();
    }
}
