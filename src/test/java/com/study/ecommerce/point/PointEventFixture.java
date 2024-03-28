package com.study.ecommerce.point;

import com.study.ecommerce.point.pointevent.dto.PointEventCreateRequest;

import java.time.Duration;
import java.time.LocalDateTime;

public class PointEventFixture {
    public static final LocalDateTime STARTED_AT = LocalDateTime.now();
    public static final LocalDateTime ENDED_AT = LocalDateTime.now().plusDays(10);
    public static final long BASE_AMOUNT = 100;
    public static final long DAILY_POINT_INCREMENT = 100;

    public static final int WEEK_ATTENDANCE_COUNT = 7;
    public static final long AMOUNT = 2000;

    public static final Duration EXPIRATION_ONE_DAY = Duration.ofDays(1);

    public static final Duration EXPIRATION_ONE_YEAR = Duration.ofDays(365);

    public static PointEventCreateRequest createWeekAttendancePointEvent() {
        return PointEventCreateRequest.builder()
                .description("1 week attendance point event")
                .startedAt(STARTED_AT.toString())
                .endedAt(ENDED_AT.toString())
                .type("ATTENDANCE")
                .expirationDuration(EXPIRATION_ONE_YEAR.toString())
                .baseAmount(BASE_AMOUNT)
                .dailyPointIncrement(DAILY_POINT_INCREMENT)
                .attendanceCount(WEEK_ATTENDANCE_COUNT)
                .build();
    }

    public static PointEventCreateRequest createOneTimePointEvent() {
        return PointEventCreateRequest.builder()
                .description("one time point event")
                .type("ONE_TIME")
                .startedAt(STARTED_AT.toString())
                .endedAt(ENDED_AT.toString())
                .expirationDuration(EXPIRATION_ONE_DAY.toString())
                .amount(AMOUNT)
                .build();
    }
}
