package com.study.ecommerce.point.exception;

import com.study.ecommerce.BaseException;
import org.springframework.http.HttpStatus;

public class EventAlreadyParticipatedException extends BaseException {
    public EventAlreadyParticipatedException() {
        super("이미 참여한 이벤트입니다.", HttpStatus.BAD_REQUEST);
    }
}
