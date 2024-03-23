package com.study.ecommerce.order;

import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderItems;
import com.study.ecommerce.order.domain.SellerOrder;
import com.study.ecommerce.order.repository.OrderItemRepository;
import com.study.ecommerce.order.repository.OrderRepository;
import com.study.ecommerce.order.repository.SellerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerOrderService {
    private final SellerOrderRepository sellerOrderRepository;
    private final OrderItemRepository orderItemRepository;

    public void createSellerOrder(Long sellerId, List<OrderItem> orderItems) {
        SellerOrder sellerOrder = SellerOrder.of(sellerId, orderItems);
        sellerOrderRepository.save(sellerOrder);
    }
}
