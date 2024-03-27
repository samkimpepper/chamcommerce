package com.study.ecommerce.member;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        return MemberResponse.of(member);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void earnPoints(Long memberId, long points) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        member.earnPoints(points);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void usePoints(Long memberId, long points) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        member.usePoints(points);
    }
}
