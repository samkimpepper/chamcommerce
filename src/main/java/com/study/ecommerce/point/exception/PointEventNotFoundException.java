package com.study.ecommerce.point.exception;

import com.study.ecommerce.BaseException;
import org.springframework.http.HttpStatus;

public class PointEventNotFoundException extends BaseException {
    public PointEventNotFoundException() {
        super("존재하지 않는 이벤트입니다.", HttpStatus.NOT_FOUND);
    }
}
