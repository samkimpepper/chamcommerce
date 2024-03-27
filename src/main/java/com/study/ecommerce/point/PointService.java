package com.study.ecommerce.point;

import com.study.ecommerce.member.Member;
import com.study.ecommerce.point.event.PointEarnedEvent;
import com.study.ecommerce.point.event.PointUsedEvent;
import com.study.ecommerce.point.strategy.PointStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointHistoryRepository pointHistoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void earnPoints(Long memberId, long amount, PointStrategy pointStrategy) {

        long points = pointStrategy.calculate(amount);

        PointHistory pointHistory = PointHistory.of(memberId, points, pointStrategy.getPointType(), pointStrategy.getDescription());
        pointHistoryRepository.save(pointHistory);

        eventPublisher.publishEvent(new PointEarnedEvent(memberId, points));
    }

    @Transactional
    public void usePoints(Long memberId, long amount) {
        PointHistory pointHistory = PointHistory.of(memberId, -amount, PointType.USE_PURCHASE, "포인트 사용");
        pointHistoryRepository.save(pointHistory);

        eventPublisher.publishEvent(new PointUsedEvent(memberId, amount));
    }

    @Transactional(readOnly = true)
    public List<PointHistoryResponse> getPointHistories(Long memberId) {
        List<PointHistory> pointHistories = pointHistoryRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);

        return pointHistories.stream()
                .map(PointHistoryResponse::of)
                .toList();
    }
}
