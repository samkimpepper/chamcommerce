package com.study.ecommerce.point.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointEarnedEvent {
    private Long memberId;
    private long points;
}
