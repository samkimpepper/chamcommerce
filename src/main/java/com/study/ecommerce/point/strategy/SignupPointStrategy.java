package com.study.ecommerce.point.strategy;

import com.study.ecommerce.point.domain.PointType;

public class SignupPointStrategy implements PointStrategy {
    public static final long POINT_AMOUNT = 2000;

    @Override
    public long calculate(long amount) {
        return POINT_AMOUNT;
    }

    @Override
    public PointType getPointType() {
        return PointType.EARN_SIGNUP;
    }

    @Override
    public String getDescription() {
        return "회원가입 포인트";
    }
}
