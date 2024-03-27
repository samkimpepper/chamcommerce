package com.study.ecommerce.point;

import com.study.ecommerce.auth.token.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointHistoryController {
    private final PointService pointService;

    @GetMapping("/point-histories")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<PointHistoryResponse>> showPointHistories(@AuthenticationPrincipal MemberDetails member) {
        return ResponseEntity.ok(pointService.getPointHistories(member.getId()));
    }
}
