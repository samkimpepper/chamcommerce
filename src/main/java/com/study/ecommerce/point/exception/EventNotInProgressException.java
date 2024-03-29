package com.study.ecommerce.point.exception;

import com.study.ecommerce.BaseException;
import org.springframework.http.HttpStatus;

public class EventNotInProgressException extends BaseException {
    public EventNotInProgressException() {
        super("이벤트 기간이 아닙니다.", HttpStatus.BAD_REQUEST);
    }
}
