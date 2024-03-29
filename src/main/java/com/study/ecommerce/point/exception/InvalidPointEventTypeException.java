package com.study.ecommerce.point.exception;

import com.study.ecommerce.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidPointEventTypeException extends BaseException {
    public InvalidPointEventTypeException() {
        super("유효하지 않은 포인트 이벤트 타입입니다.", HttpStatus.BAD_REQUEST);
    }
}
