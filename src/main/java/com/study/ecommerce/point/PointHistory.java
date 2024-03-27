package com.study.ecommerce.point;

import com.study.ecommerce.member.Member;
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

    public static PointHistory of(Long memberId, long amount, PointType type, String description) {
        return PointHistory.builder()
                .memberId(memberId)
                .amount(amount)
                .type(type)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
