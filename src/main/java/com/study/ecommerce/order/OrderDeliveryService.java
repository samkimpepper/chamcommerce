package com.study.ecommerce.order;

import com.study.ecommerce.delivery.domain.Delivery;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.SellerOrder;
import com.study.ecommerce.order.repository.OrderRepository;
import com.study.ecommerce.order.repository.SellerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDeliveryService {
    private final OrderRepository orderRepository;
    private final SellerOrderRepository sellerOrderRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registerDelivery(Order order, SellerOrder sellerOrder, Delivery delivery) {
        order.registerDelivery(delivery);
        sellerOrder.registerDelivery(delivery);

        orderRepository.save(order);
        sellerOrderRepository.save(sellerOrder);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeDelivery(Order order, SellerOrder sellerOrder) {
        order.completeDelivery();
        sellerOrder.completeDelivery();

        orderRepository.save(order);
        sellerOrderRepository.save(sellerOrder);
    }
}
