package com.study.ecommerce.point.pointevent.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @ManyToOne
    private PointEvent event;

    @Setter
    private LocalDateTime participatedAt;

    @Setter
    private int continuousCount;

    public void plusContinuousCount() {
        continuousCount++;
    }


    public static EventParticipation of(Long memberId, OneTimePointEvent event, LocalDateTime participatedAt) {
        return EventParticipation.builder()
            .memberId(memberId)
            .event(event)
            .participatedAt(participatedAt)
            .continuousCount(1)
            .build();
    }

    public static EventParticipation of(Long memberId, AttendancePointEvent event, LocalDateTime participatedAt) {
        return EventParticipation.builder()
            .memberId(memberId)
            .event(event)
            .participatedAt(participatedAt)
            .continuousCount(0)
            .build();
    }
}
