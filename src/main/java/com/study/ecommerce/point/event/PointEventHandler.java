package com.study.ecommerce.point.event;

import com.study.ecommerce.member.Member;
import com.study.ecommerce.member.MemberRepository;
import com.study.ecommerce.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PointEventHandler {
    private final MemberService memberService;

    @TransactionalEventListener
    public void onPointEarned(PointEarnedEvent event) {
        memberService.earnPoints(event.getMemberId(), event.getPoints());
    }

    @TransactionalEventListener
    public void onPointUsed(PointUsedEvent event) {
        memberService.usePoints(event.getMemberId(), event.getPoints());
    }

}
