package com.study.ecommerce.point;

import com.study.ecommerce.member.Member;
import com.study.ecommerce.member.MemberRepository;
import com.study.ecommerce.order.domain.Order;
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
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void earnPoints(Long memberId, long amount, PointStrategy pointStrategy, Order order) {

        long points = pointStrategy.calculate(amount);

        PointHistory pointHistory = PointHistory.of(memberId, points, pointStrategy.getPointType(), order, pointStrategy.getDescription());
        pointHistoryRepository.save(pointHistory);

        eventPublisher.publishEvent(new PointEarnedEvent(memberId, points));
    }

    @Transactional
    public void usePoints(Long memberId, int orderAmount, long amount, Order order) {

        PointHistory pointHistory = PointHistory.of(memberId, amount, PointType.USE_PURCHASE, order, "포인트 사용");
        pointHistoryRepository.save(pointHistory);

        eventPublisher.publishEvent(new PointUsedEvent(memberId, amount));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void refundPoints(Order order) {
        PointHistory useHistory = pointHistoryRepository.findByOrder(order)
                .orElseThrow(() -> new IllegalArgumentException("포인트 환불 대상이 존재하지 않습니다."));

        PointHistory refundHistory = PointHistory.of(order.getCustomerId(), useHistory.getAmount(), PointType.REFUND_PURCHASE, order, "구매 취소");
        pointHistoryRepository.save(refundHistory);

        eventPublisher.publishEvent(new PointEarnedEvent(order.getCustomerId(), useHistory.getAmount()));
    }

    @Transactional(readOnly = true)
    public List<PointHistoryResponse> getPointHistories(Long memberId) {
        List<PointHistory> pointHistories = pointHistoryRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);

        return pointHistories.stream()
                .map(PointHistoryResponse::of)
                .toList();
    }
}
