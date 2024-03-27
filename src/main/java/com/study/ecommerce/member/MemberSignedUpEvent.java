package com.study.ecommerce.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSignedUpEvent {
    private final Long memberId;
    private final Role role;
}
