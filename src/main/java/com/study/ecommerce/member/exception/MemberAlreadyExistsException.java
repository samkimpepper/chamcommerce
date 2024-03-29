package com.study.ecommerce.member.exception;

import com.study.ecommerce.BaseException;
import org.springframework.http.HttpStatus;

public class MemberAlreadyExistsException extends BaseException {
    public MemberAlreadyExistsException() {
        super("이미 존재하는 회원입니다.", HttpStatus.BAD_REQUEST);
    }
}
