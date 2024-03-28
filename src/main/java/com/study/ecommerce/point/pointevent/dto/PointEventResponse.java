package com.study.ecommerce.point.pointevent.dto;

import com.study.ecommerce.point.pointevent.domain.AttendancePointEvent;
import com.study.ecommerce.point.pointevent.domain.OneTimePointEvent;
import com.study.ecommerce.point.pointevent.domain.PointEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointEventResponse {
    private Long id;
    private String type;
    private String description;
    private String startedAt;
    private String endedAt;

    // AttendancePointEvent fields
    private long baseAmount;
    private long dailyPointIncrement;

    // oneTimePointEvent fields
    private long amount;

    public static PointEventResponse of(PointEvent event) {
        if (event instanceof AttendancePointEvent) {
            return of((AttendancePointEvent) event);
        } else if (event instanceof OneTimePointEvent) {
            return of((OneTimePointEvent) event);
        }
        return null;
    }

    public static PointEventResponse of(AttendancePointEvent event) {
        return PointEventResponse.builder()
                .id(event.getId())
                .type("ATTENDANCE")
                .description(event.getDescription())
                .startedAt(event.getStartedAt().toString())
                .endedAt(event.getEndedAt().toString())
                .baseAmount(event.getBaseAmount())
                .dailyPointIncrement(event.getDailyPointIncrement())
                .build();
    }

    public static PointEventResponse of(OneTimePointEvent event) {
        return PointEventResponse.builder()
                .id(event.getId())
                .type("ONE_TIME")
                .description(event.getDescription())
                .startedAt(event.getStartedAt().toString())
                .endedAt(event.getEndedAt().toString())
                .amount(event.getAmount())
                .build();
    }
}
