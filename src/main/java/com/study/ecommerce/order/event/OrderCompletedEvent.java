package com.study.ecommerce.order.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCompletedEvent {
    private Long memberId;
    private int totalAmount;
}
