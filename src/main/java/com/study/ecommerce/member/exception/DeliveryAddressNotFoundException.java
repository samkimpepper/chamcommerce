package com.study.ecommerce.member.exception;

import com.study.ecommerce.BaseException;
import org.springframework.http.HttpStatus;

public class DeliveryAddressNotFoundException extends BaseException {
    public DeliveryAddressNotFoundException() {
        super("배송지가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }
}
