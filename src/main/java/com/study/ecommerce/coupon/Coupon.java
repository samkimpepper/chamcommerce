package com.study.ecommerce.coupon;

import com.study.ecommerce.member.domain.MemberGrade;
import com.study.ecommerce.point.DurationConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int discountAmount;

    private int minimumOrderAmount;

    private int discountRate;

    @Column(columnDefinition = "VARCHAR(50)")
    @Convert(converter = DurationConverter.class)
    private Duration expirationDuration;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    public static Coupon gradeOf(MemberGrade memberGrade, CouponType type, String description, int minimumOrderAmount, int discount) {
        if (type.equals(CouponType.DISCOUNT_AMOUNT)) {
            return Coupon.builder()
                    .name("멤버십 전용 쿠폰")
                    .memberGrade(memberGrade)
                    .description(description)
                    .type(type)
                    .discountAmount(discount)
                    .minimumOrderAmount(minimumOrderAmount)
                    .expirationDuration(Duration.ofDays(30))
                    .build();
        } else if (type.equals(CouponType.DISCOUNT_PRATE)) {
            return Coupon.builder()
                    .name("멤버십 전용 쿠폰")
                    .memberGrade(memberGrade)
                    .description(description)
                    .type(type)
                    .discountRate(discount)
                    .minimumOrderAmount(minimumOrderAmount)
                    .expirationDuration(Duration.ofDays(30))
                    .build();
        }

        return Coupon.builder()
                .name("멤버십 전용 쿠폰")
                .memberGrade(memberGrade)
                .description(description)
                .type(CouponType.FREE_SHIPPING)
                .minimumOrderAmount(minimumOrderAmount)
                .expirationDuration(Duration.ofDays(30))
                .build();
    }

}
