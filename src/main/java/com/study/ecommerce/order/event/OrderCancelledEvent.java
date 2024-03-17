package com.study.ecommerce.order.event;

import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class OrderCancelledEvent {
    private OrderItems orderItems;
}
