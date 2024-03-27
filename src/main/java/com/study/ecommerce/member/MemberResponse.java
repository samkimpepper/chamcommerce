package com.study.ecommerce.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {
    private final Long id;
    private final String email;
    private final String role;
    private final long points;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .role(member.getRoles().stream().findFirst().get().name())
                .points(member.getPoints())
                .build();
    }
}
