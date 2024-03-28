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
@DiscriminatorValue("ONE_TIME")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OneTimePointEvent extends PointEvent {
    private long amount;

    public OneTimePointEvent(String description, LocalDateTime startedAt, LocalDateTime endedAt, Duration expirationDuration, long amount) {
        super(null, description, startedAt, endedAt, expirationDuration);
        this.amount = amount;
    }
}
