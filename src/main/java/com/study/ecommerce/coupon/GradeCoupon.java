package com.study.ecommerce.coupon;

import com.study.ecommerce.member.domain.MemberGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
@RequiredArgsConstructor
public class GradeCoupon {
    private final CouponRepository couponRepository;
    private final Map<MemberGrade, List<Coupon>> gradeCoupons = new HashMap<>();

    public void initGradeCoupons() {
        saveCoupons(MemberGrade.WHITE, Arrays.asList(
                Coupon.gradeOf(MemberGrade.WHITE, CouponType.DISCOUNT_PRATE, "5만원 이상 구매 시 3% 할인", 50000, 3),
                Coupon.gradeOf(MemberGrade.WHITE, CouponType.FREE_SHIPPING, "2만원 이상 구매 시 무료배송", 20000, 0)
        ));
        saveCoupons(MemberGrade.RED, Arrays.asList(
                Coupon.gradeOf(MemberGrade.RED, CouponType.DISCOUNT_PRATE, "3만원 이상 구매 시 5% 할인", 30000, 5),
                Coupon.gradeOf(MemberGrade.RED, CouponType.FREE_SHIPPING, "만원 이상 구매 시 무료배송", 10000, 0)
        ));
        saveCoupons(MemberGrade.VIP, Arrays.asList(
                Coupon.gradeOf(MemberGrade.VIP, CouponType.DISCOUNT_PRATE, "3만원 이상 구매 시 5% 할인", 30000, 5),
                Coupon.gradeOf(MemberGrade.VIP, CouponType.DISCOUNT_PRATE, "3만원 이상 구매 시 3% 할인", 30000, 3),
                Coupon.gradeOf(MemberGrade.VIP, CouponType.DISCOUNT_AMOUNT, "5만원 이상 구매 시 3,000원 할인", 50000, 3000),
                Coupon.gradeOf(MemberGrade.VIP, CouponType.FREE_SHIPPING, "만원 이상 구매 시 무료배송", 10000, 0)
        ));
        saveCoupons(MemberGrade.VIP_GOLD, Arrays.asList(
                Coupon.gradeOf(MemberGrade.VIP_GOLD, CouponType.DISCOUNT_PRATE, "3만원 이상 구매 시 10% 할인", 30000, 10),
                Coupon.gradeOf(MemberGrade.VIP_GOLD, CouponType.DISCOUNT_PRATE, "3만원 이상 구매 시 5% 할인", 30000, 5),
                Coupon.gradeOf(MemberGrade.VIP_GOLD, CouponType.DISCOUNT_AMOUNT, "7만원 이상 구매 시 5,000원 할인", 70000, 5000),
                Coupon.gradeOf(MemberGrade.VIP_GOLD, CouponType.FREE_SHIPPING, "무료배송", 0, 0)
        ));
        saveCoupons(MemberGrade.VVIP, Arrays.asList(
                Coupon.gradeOf(MemberGrade.VVIP, CouponType.DISCOUNT_PRATE, "3만원 이상 구매 시 10% 할인", 30000, 10),
                Coupon.gradeOf(MemberGrade.VVIP, CouponType.DISCOUNT_PRATE, "3만원 이상 구매 시 5% 할인", 30000, 5),
                Coupon.gradeOf(MemberGrade.VVIP, CouponType.DISCOUNT_AMOUNT, "20만원 이상 구매 시 30,000원 할인", 200000, 30000),
                Coupon.gradeOf(MemberGrade.VVIP, CouponType.DISCOUNT_AMOUNT, "10만원 이상 구매 시 10,000원 할인", 100000, 10000)
        ));
    }

    private void saveCoupons(MemberGrade grade, List<Coupon> coupons) {
        List<Coupon> savedCoupons = couponRepository.saveAll(coupons);
        gradeCoupons.put(grade, savedCoupons);
    }

    public List<Coupon> getGradeCoupons(MemberGrade grade) {
        return gradeCoupons.getOrDefault(grade, Collections.emptyList());
    }
}
