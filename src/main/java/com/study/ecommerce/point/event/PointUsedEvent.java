package com.study.ecommerce.point.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointUsedEvent {
    private Long memberId;
    private long points;
}
