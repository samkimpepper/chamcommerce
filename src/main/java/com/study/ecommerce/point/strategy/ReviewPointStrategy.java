package com.study.ecommerce.point.strategy;

import com.study.ecommerce.point.PointType;

public class ReviewPointStrategy implements PointStrategy {
    private static final long POINT_AMOUNT = 100;

    @Override
    public long calculate(long amount) {
        return POINT_AMOUNT;
    }

    @Override
    public PointType getPointType() {
        return PointType.EARN_REVIEW;
    }

    @Override
    public String getDescription() {
        return "리뷰 작성";
    }
}
