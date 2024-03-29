package com.study.ecommerce.point.pointevent;

import com.study.ecommerce.point.exception.InvalidPointEventTypeException;
import com.study.ecommerce.point.pointevent.domain.AttendancePointEvent;
import com.study.ecommerce.point.pointevent.domain.OneTimePointEvent;
import com.study.ecommerce.point.pointevent.domain.PointEvent;
import com.study.ecommerce.point.pointevent.dto.PointEventCreateRequest;
import com.study.ecommerce.point.pointevent.dto.PointEventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointEventService {
    private final PointEventRepository pointEventRepository;

    public PointEventResponse createPointEvent(PointEventCreateRequest request) {
        if (request.getType().equals("ATTENDANCE")) {
            AttendancePointEvent event = request.toAttendancePointEvent();
            pointEventRepository.save(event);

            return PointEventResponse.of(event);
        } else if (request.getType().equals("ONE_TIME")) {
            OneTimePointEvent event = request.toOneTimePointEvent();
            pointEventRepository.save(event);

            return PointEventResponse.of(event);
        }
        throw new InvalidPointEventTypeException();
    }

    public List<PointEventResponse> getOngoingPointEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<PointEvent> ongoingEvents = pointEventRepository.findByStartedAtBeforeAndEndedAtAfter(now, now);

        return ongoingEvents.stream()
                .map(PointEventResponse::of)
                .toList();
    }
}
