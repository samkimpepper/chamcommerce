package com.study.ecommerce.point;

import com.study.ecommerce.point.domain.PointHistory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointHistoryResponse {
    private Long id;
    private long amount;
    private String type;
    private String description;
    private String createdAt;
    private String expiredAt;

    public static PointHistoryResponse of(PointHistory pointHistory) {
        return PointHistoryResponse.builder()
                .id(pointHistory.getId())
                .amount(pointHistory.getAmount())
                .type(pointHistory.getType().name())
                .description(pointHistory.getDescription())
                .createdAt(pointHistory.getCreatedAt().toString())
                .expiredAt((pointHistory.getExpiredAt() == null) ? "" : pointHistory.getExpiredAt().toString())
                .build();
    }
}
