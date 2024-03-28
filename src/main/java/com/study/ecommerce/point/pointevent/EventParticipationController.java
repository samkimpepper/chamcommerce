package com.study.ecommerce.point.pointevent;

import com.study.ecommerce.auth.token.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventParticipationController {
    private final EventParticipationService eventParticipationService;

    @PostMapping("/events/{eventId}/participate")
    public ResponseEntity<Void> participate(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable Long eventId) {
        eventParticipationService.participate(member.getId(), eventId);
        return ResponseEntity.ok().build();
    }
}
