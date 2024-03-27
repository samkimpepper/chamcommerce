package com.study.ecommerce.point;

import com.study.ecommerce.member.Member;

public class PointUsePolicy {
    private static final int MINIMUM_ORDER_AMOUNT = 20000;

    public static void isAvailable(Member member, int orderAmount, long pointAmount) {
        if (member.getPoints() < pointAmount) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }
        if (pointAmount > orderAmount) {
            throw new IllegalArgumentException("포인트 사용 금액이 가격보다 클 수 없습니다.");
        }
        if (orderAmount < MINIMUM_ORDER_AMOUNT) {
            throw new IllegalArgumentException("포인트 사용 최소 금액을 충족하지 못했습니다.");
        }
    }
}
