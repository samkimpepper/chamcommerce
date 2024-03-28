package com.study.ecommerce.point.pointevent;

import com.study.ecommerce.point.pointevent.dto.PointEventCreateRequest;
import com.study.ecommerce.point.pointevent.dto.PointEventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointEventController {
    private final PointEventService pointEventService;

    @PostMapping("/point-events")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PointEventResponse> createPointEvent(@RequestBody PointEventCreateRequest request) {
        PointEventResponse response = pointEventService.createPointEvent(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/point-events/ongoing")
    public ResponseEntity<List<PointEventResponse>> getOngoingPointEvents() {
        List<PointEventResponse> responses = pointEventService.getOngoingPointEvents();
        return ResponseEntity.ok(responses);
    }
}
