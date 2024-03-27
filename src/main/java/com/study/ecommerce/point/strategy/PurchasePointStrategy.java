package com.study.ecommerce.point.strategy;

import com.study.ecommerce.point.PointType;

public class PurchasePointStrategy implements PointStrategy {
    private static final double POINT_RATE = 0.01;

    @Override
    public long calculate(long amount) {
        return (long) (amount * POINT_RATE);
    }

    @Override
    public PointType getPointType() {
        return PointType.EARN_PURCHASE;
    }

    @Override
    public String getDescription() {
        return "구매 포인트 적립";
    }
}
