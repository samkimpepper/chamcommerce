package com.study.ecommerce.point.domain;

import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.point.pointevent.domain.EventParticipation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private long amount;

    @Enumerated(EnumType.STRING)
    private PointType type;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    @ManyToOne
    private Order order;

    @ManyToOne
    private EventParticipation participation;

    public static PointHistory of(Long memberId, long amount, PointType type, Order order, String description) {
        return PointHistory.builder()
                .memberId(memberId)
                .order(order)
                .amount(amount)
                .type(type)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static PointHistory of(Long memberId, long amount, PointType type, EventParticipation participation, String description) {
        return PointHistory.builder()
                .memberId(memberId)
                .participation(participation)
                .amount(amount)
                .type(type)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
