package com.study.ecommerce.order.event;

import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.event.OrderCreatedEvent;
import com.study.ecommerce.product.domain.ProductItem;
import com.study.ecommerce.product.domain.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCreatedEventHandler {

    private final ProductItemRepository productItemRepository;

    @EventListener
    public void handle(OrderCreatedEvent event) {
        List<OrderItem> orderItems = event.getOrderItems();

        for (OrderItem orderItem : orderItems) {

            ProductItem productItem = orderItem.getProductItem();
            productItem.decreaseStock(orderItem.getQuantity());
            productItemRepository.save(productItem);
        }
    }
}
