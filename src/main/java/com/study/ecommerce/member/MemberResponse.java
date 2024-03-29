package com.study.ecommerce.member;

import com.study.ecommerce.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {
    private final Long id;
    private final String email;
    private final String role;
    private final long points;
    private final String grade;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .role(member.getRoles().stream().findFirst().get().name())
                .points(member.getPoints())
                .grade(member.getGrade().name())
                .build();
    }
}
