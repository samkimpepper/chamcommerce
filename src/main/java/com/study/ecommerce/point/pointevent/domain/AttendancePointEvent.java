package com.study.ecommerce.point.pointevent.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("ATTENDANCE")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AttendancePointEvent extends PointEvent {
    private long baseAmount;
    private long dailyPointIncrement;
    private int attendanceCount;

    public long calculatePoint(int continuousCount) {
        return baseAmount + dailyPointIncrement * continuousCount;
    }

    public AttendancePointEvent(String description, LocalDateTime startedAt, LocalDateTime endedAt, Duration expirationDuration, long baseAmount, long dailyPointIncrement, int attendanceCount) {
        super(null, description, startedAt, endedAt, expirationDuration);
        this.baseAmount = baseAmount;
        this.dailyPointIncrement = dailyPointIncrement;
        this.attendanceCount = attendanceCount;
    }
}
