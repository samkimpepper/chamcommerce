package com.study.ecommerce.member.exception;

import com.study.ecommerce.BaseException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends BaseException {
    public MemberNotFoundException() {
        super("존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND);
    }
}
