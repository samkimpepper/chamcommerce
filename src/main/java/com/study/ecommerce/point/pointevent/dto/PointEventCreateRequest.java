package com.study.ecommerce.point.pointevent.dto;

import com.study.ecommerce.point.pointevent.domain.AttendancePointEvent;
import com.study.ecommerce.point.pointevent.domain.OneTimePointEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Builder
public class PointEventCreateRequest {
    private String description;
    private String startedAt;
    private String endedAt;
    private String type;

    // AttendancePointEvent fields
    private long baseAmount;
    private long dailyPointIncrement;
    private int attendanceCount;
    private String expirationDuration;

    // OneTimePointEvent fields
    private long amount;

    public AttendancePointEvent toAttendancePointEvent() {
        return AttendancePointEvent.builder()
                .description(description)
                .startedAt(LocalDateTime.parse(startedAt))
                .endedAt(LocalDateTime.parse(endedAt))
                .expirationDuration(Duration.parse(expirationDuration))
                .baseAmount(baseAmount)
                .dailyPointIncrement(dailyPointIncrement)
                .attendanceCount(attendanceCount)
                .build();
    }

    public OneTimePointEvent toOneTimePointEvent() {
        return OneTimePointEvent.builder()
                .description(description)
                .startedAt(LocalDateTime.parse(startedAt))
                .endedAt(LocalDateTime.parse(endedAt))
                .expirationDuration(Duration.parse(expirationDuration))
                .amount(amount)
                .build();
    }
}
