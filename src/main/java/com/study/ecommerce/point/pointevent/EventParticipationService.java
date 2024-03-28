package com.study.ecommerce.point.pointevent;

import com.study.ecommerce.point.PointService;
import com.study.ecommerce.point.domain.PointType;
import com.study.ecommerce.point.pointevent.domain.AttendancePointEvent;
import com.study.ecommerce.point.pointevent.domain.EventParticipation;
import com.study.ecommerce.point.pointevent.domain.OneTimePointEvent;
import com.study.ecommerce.point.pointevent.domain.PointEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventParticipationService {
    private final PointEventRepository pointEventRepository;
    private final EventParticipationRepository eventParticipationRepository;
    private final PointService pointService;

    @Transactional
    public void participate(Long memberId, Long eventId) {
        PointEvent event = pointEventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이벤트입니다."));

        if (event instanceof AttendancePointEvent) {
            AttendancePointEvent attendanceEvent = (AttendancePointEvent) event;
            processAttendancePointEvent(attendanceEvent, memberId);

        } else if (event instanceof OneTimePointEvent) {
            OneTimePointEvent oneTimeEvent = (OneTimePointEvent) event;
            processOneTimePointEvent(oneTimeEvent, memberId);
        }
    }

    private void processOneTimePointEvent(OneTimePointEvent event, Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(event.getStartedAt()) || now.isAfter(event.getEndedAt())) {
            throw new IllegalArgumentException("이벤트 기간이 아닙니다.");
        }

        if (eventParticipationRepository.existsByMemberIdAndEventId(memberId, event.getId())) {
            throw new IllegalArgumentException("이미 참여한 이벤트입니다.");
        }

        EventParticipation participation = EventParticipation.of(memberId, event, now);
        eventParticipationRepository.save(participation);

        long point = event.getAmount();
        pointService.earnPoints(participation.getMemberId(), point, PointType.EARN_EVENT, participation);
    }

    private void processAttendancePointEvent(AttendancePointEvent event, Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(event.getStartedAt()) || now.isAfter(event.getEndedAt())) {
            throw new IllegalArgumentException("이벤트 기간이 아닙니다.");
        }

        EventParticipation participation = eventParticipationRepository.findByMemberIdAndEventId(memberId, event.getId())
                .orElseGet(() -> {
                    EventParticipation newParticipation = EventParticipation.of(memberId, event, now);
                    return eventParticipationRepository.save(newParticipation);
                });

        if (participation.getContinuousCount() >= event.getAttendanceCount()) {
            throw new IllegalArgumentException("이벤트 참여 횟수를 초과하였습니다.");
        }
        if (participation.getParticipatedAt().toLocalDate().equals(now.toLocalDate())) {
            throw new IllegalArgumentException("오늘은 이미 참여하였습니다.");
        }

        participation.setParticipatedAt(now);

        long point = event.calculatePoint(participation.getContinuousCount());

        if (participation.getParticipatedAt().plus(event.getExpirationDuration()).isBefore(now)) {
            participation.setContinuousCount(1);
        } else {
            participation.plusContinuousCount();
        }

        pointService.earnPoints(participation.getMemberId(), point, PointType.EARN_EVENT, participation);
    }
}
