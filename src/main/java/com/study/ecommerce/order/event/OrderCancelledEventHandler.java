package com.study.ecommerce.order.event;

import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderItems;
import com.study.ecommerce.product.domain.ProductItem;
import com.study.ecommerce.product.domain.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OrderCancelledEventHandler {
    private final ProductItemRepository productItemRepository;

    @EventListener
    public void handle(OrderCancelledEvent event) {
        OrderItems orderItems = event.getOrderItems();

        for (OrderItem orderItem : orderItems) {

            ProductItem productItem = orderItem.getProductItem();
            productItem.increaseStock(orderItem.getQuantity());
            productItemRepository.save(productItem);
        }
    }
}
