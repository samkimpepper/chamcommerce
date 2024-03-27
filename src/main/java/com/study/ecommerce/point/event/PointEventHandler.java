package com.study.ecommerce.point.event;

import com.study.ecommerce.member.Member;
import com.study.ecommerce.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PointEventHandler {
    private final MemberRepository memberRepository;

    @TransactionalEventListener
    public void onPointEarned(PointEarnedEvent event) {
        Member member = memberRepository.findById(event.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        member.earnPoints(event.getPoints());
        //memberRepository.save(member);
    }

    @TransactionalEventListener
    public void onPointUsed(PointUsedEvent event) {
        Member member = memberRepository.findById(event.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        member.usePoints(event.getPoints());
        //memberRepository.save(member);
    }

}
