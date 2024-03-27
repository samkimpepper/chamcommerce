package com.study.ecommerce.member;

import com.study.ecommerce.point.PointService;
import com.study.ecommerce.point.strategy.SignupPointStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberEventHandler {
    private final PointService pointService;

    @EventListener
    public void onSignUp(MemberSignedUpEvent event) {
        if (event.getRole() == Role.ROLE_CUSTOMER)
            pointService.earnPoints(event.getMemberId(), 0, new SignupPointStrategy(), null);
    }
}
