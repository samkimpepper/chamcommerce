package com.study.ecommerce.coupon;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @ManyToOne
    private Coupon coupon;

    private boolean used;

    private LocalDateTime issuedAt;

    private LocalDateTime usedAt;

    private LocalDateTime expiredAt;

    public static MemberCoupon issue(Long memberId, Coupon coupon) {
        return MemberCoupon.builder()
                .memberId(memberId)
                .coupon(coupon)
                .used(false)
                .issuedAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plus(coupon.getExpirationDuration()))
                .build();
    }
}
