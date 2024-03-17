package com.study.ecommerce.order;


import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.product.domain.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderCreatedEvent {
    private List<OrderItem> orderItems;

}
