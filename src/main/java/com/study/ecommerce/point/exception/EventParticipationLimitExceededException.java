package com.study.ecommerce.point.exception;

import com.study.ecommerce.BaseException;
import org.springframework.http.HttpStatus;

public class EventParticipationLimitExceededException extends BaseException {
    public EventParticipationLimitExceededException() {
        super("이벤트 참여 횟수를 초과하였습니다.", HttpStatus.BAD_REQUEST);
    }
}
