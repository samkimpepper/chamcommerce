package com.study.ecommerce.point.strategy;

import com.study.ecommerce.point.domain.PointType;

public interface PointStrategy {
    long calculate(long amount);

    PointType getPointType();

    String getDescription();
}
