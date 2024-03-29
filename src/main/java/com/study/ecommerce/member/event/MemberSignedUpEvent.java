package com.study.ecommerce.member.event;

import com.study.ecommerce.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSignedUpEvent {
    private final Long memberId;
    private final Role role;
}
