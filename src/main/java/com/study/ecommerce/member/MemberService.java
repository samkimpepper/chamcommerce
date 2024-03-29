package com.study.ecommerce.member;

import com.study.ecommerce.member.domain.Member;
import com.study.ecommerce.member.domain.MemberGrade;
import com.study.ecommerce.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(MemberNotFoundException::new);

        return MemberResponse.of(member);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void earnPoints(Long memberId, long points) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.earnPoints(points);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void usePoints(Long memberId, long points) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.usePoints(points);
    }

    @Transactional
    public void updateMemberGrade(Long memberId, MemberGrade memberGrade) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.setGrade(memberGrade);
    }
}
