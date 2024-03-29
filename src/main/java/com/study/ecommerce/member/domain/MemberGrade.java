package com.study.ecommerce.member.domain;

public enum MemberGrade {
    WHITE(0, 0),
    RED(100000, 1),
    VIP(200000, 3),
    VIP_GOLD(500000, 5),
    VVIP(3000000, 0);

    private final int minTotalAmount;
    private final int minTotalCount;

    MemberGrade(int minTotalAmount, int minTotalCount) {
        this.minTotalAmount = minTotalAmount;
        this.minTotalCount = minTotalCount;
    }

    public int getMinTotalAmount() {
        return minTotalAmount;
    }

    public int getMinTotalCount() {
        return minTotalCount;
    }

    public static MemberGrade findGrade(long totalAmount, int totalCount) {
        MemberGrade newGrade = WHITE;
        for (MemberGrade grade : values()) {
            if (totalAmount >= grade.getMinTotalAmount() && totalCount >= grade.getMinTotalCount()) {
                newGrade = grade;
            } else {
                break;
            }
        }
        return newGrade;
    }
}
