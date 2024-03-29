package com.study.ecommerce.coupon;

import com.study.ecommerce.member.domain.Member;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final GradeCoupon gradeCoupon;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

//    public void initGradeCoupons() {
//        gradeCoupon.initGradeCoupons();
//    }

    @Transactional
    public void issueCoupon(Long memberId, Coupon coupon) {
        MemberCoupon memberCoupon = MemberCoupon.issue(memberId, coupon);
        memberCouponRepository.save(memberCoupon);
    }

    @Transactional
    public void issueGradeCoupons(Member member) {
        gradeCoupon.getGradeCoupons(member.getGrade()).forEach(coupon -> {
            issueCoupon(member.getId(), coupon);
        });
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> getMemberCoupons(Long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId).stream()
                .map(MemberCouponResponse::from)
                .toList();
    }
}
