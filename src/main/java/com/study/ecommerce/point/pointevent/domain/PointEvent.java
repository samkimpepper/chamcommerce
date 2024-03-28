package com.study.ecommerce.point.pointevent.domain;

import com.study.ecommerce.point.DurationConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "event_type")
public class PointEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @Column(columnDefinition = "VARCHAR(50)")
    @Convert(converter = DurationConverter.class)
    private Duration expirationDuration;
}
