package com.study.ecommerce.point.pointevent;

import com.study.ecommerce.point.PointService;
import com.study.ecommerce.point.domain.PointType;
import com.study.ecommerce.point.exception.EventAlreadyParticipatedException;
import com.study.ecommerce.point.exception.EventNotInProgressException;
import com.study.ecommerce.point.exception.EventParticipationLimitExceededException;
import com.study.ecommerce.point.exception.PointEventNotFoundException;
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
                .orElseThrow(PointEventNotFoundException::new);

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
            throw new EventNotInProgressException();
        }

        if (eventParticipationRepository.existsByMemberIdAndEventId(memberId, event.getId())) {
            throw new EventAlreadyParticipatedException();
        }

        EventParticipation participation = EventParticipation.of(memberId, event, now);
        eventParticipationRepository.save(participation);

        long point = event.getAmount();
        pointService.earnPoints(participation.getMemberId(), point, PointType.EARN_EVENT, participation);
    }

    private void processAttendancePointEvent(AttendancePointEvent event, Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(event.getStartedAt()) || now.isAfter(event.getEndedAt())) {
            throw new EventNotInProgressException();
        }

        EventParticipation participation = eventParticipationRepository.findByMemberIdAndEventId(memberId, event.getId())
                .orElseGet(() -> {
                    EventParticipation newParticipation = EventParticipation.of(memberId, event, now);
                    return eventParticipationRepository.save(newParticipation);
                });

        if (participation.getContinuousCount() >= event.getAttendanceCount()) {
            throw new EventParticipationLimitExceededException();
        }
        if (participation.getParticipatedAt().toLocalDate().equals(now.toLocalDate())) {
            throw new IllegalArgumentException("오늘은 이미 참여하였습니다.");
        }

        participation.setParticipatedAt(now);

        long point = event.calculatePoint(participation.getContinuousCount());

        if (participation.getParticipatedAt().plus(event.getExpirationDuration()).isBefore(now)) { // 만료
            participation.setContinuousCount(1);
        } else {
            participation.plusContinuousCount();
        }

        pointService.earnPoints(participation.getMemberId(), point, PointType.EARN_EVENT, participation);
    }
}
