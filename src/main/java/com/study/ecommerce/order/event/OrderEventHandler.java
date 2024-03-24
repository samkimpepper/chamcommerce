package com.study.ecommerce.order.event;

import com.study.ecommerce.notification.NotificationService;
import com.study.ecommerce.order.SellerOrderService;
import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderItems;
import com.study.ecommerce.order.domain.SellerOrder;
import com.study.ecommerce.order.repository.OrderItemRepository;
import com.study.ecommerce.product.domain.ProductItem;
import com.study.ecommerce.product.domain.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventHandler {
    private final ProductItemRepository productItemRepository;
    private final NotificationService notificationService;
    private final SellerOrderService sellerOrderService;

    @TransactionalEventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        List<OrderItem> orderItems = event.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            ProductItem productItem = orderItem.getProductItem();
            productItem.decreaseStock(orderItem.getQuantity());
            productItemRepository.save(productItem);
        }

        notificationService.sendNotification(event.toNotification());
    }

    @TransactionalEventListener
    public void onOrderCancelled(OrderCancelledEvent event) {
        OrderItems orderItems = event.getOrderItems();

        for (OrderItem orderItem : orderItems) {

            ProductItem productItem = orderItem.getProductItem();
            productItem.increaseStock(orderItem.getQuantity());
            productItemRepository.save(productItem);
        }

        sellerOrderService.cancelSellerOrders(orderItems);

        notificationService.sendNotification(event.toNotification());
    }

    @TransactionalEventListener
    public void onOrderPlaced(OrderPlacedEvent event) {
        Map<Long, List<OrderItem>> orderItemsBySeller = event.getOrderItems().stream()
                .collect(groupingBy(orderItem -> orderItem.getProductItem().getProduct().getSellerId()));

        orderItemsBySeller.entrySet()
                        .forEach(entry -> {
                            Long sellerId = entry.getKey();
                            List<OrderItem> orderItems = entry.getValue();
                            sellerOrderService.createSellerOrder(sellerId, orderItems);
                        });

        notificationService.sendNotifications(event.toNotifications());
    }
}
