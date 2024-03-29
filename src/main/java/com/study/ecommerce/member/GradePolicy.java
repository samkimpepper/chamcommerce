package com.study.ecommerce.member;

import com.study.ecommerce.coupon.Coupon;
import com.study.ecommerce.coupon.CouponService;
import com.study.ecommerce.coupon.GradeCoupon;
import com.study.ecommerce.member.domain.Member;
import com.study.ecommerce.member.domain.MemberGrade;
import com.study.ecommerce.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GradePolicy {
    private final MemberService memberService;
    private final OrderService orderService;
    private final CouponService couponService;


    @Transactional
    public void applyGrade(Member member) {
        long totalAmountThisMonth = orderService.getTotalAmountThisMonth(member.getId());
        int totalCountThisMonth = orderService.getTotalCountThisMonth(member.getId());

        MemberGrade newGrade = MemberGrade.findGrade(totalAmountThisMonth, totalCountThisMonth);

        memberService.updateMemberGrade(member.getId(), newGrade);
    }

    public void applyGradeCoupon(Member member) {
        couponService.issueGradeCoupons(member);
    }
}
