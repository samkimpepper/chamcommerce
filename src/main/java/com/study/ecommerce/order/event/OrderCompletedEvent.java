package com.study.ecommerce.order.event;

import com.study.ecommerce.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCompletedEvent {
    private Long memberId;
    private Order order;
    private int totalAmount;
}
