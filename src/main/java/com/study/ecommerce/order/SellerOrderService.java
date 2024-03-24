package com.study.ecommerce.order;

import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderItems;
import com.study.ecommerce.order.domain.SellerOrder;
import com.study.ecommerce.order.dto.SellerOrderResponse;
import com.study.ecommerce.order.repository.OrderItemRepository;
import com.study.ecommerce.order.repository.OrderRepository;
import com.study.ecommerce.order.repository.SellerOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerOrderService {
    private final SellerOrderRepository sellerOrderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createSellerOrder(Long sellerId, List<OrderItem> orderItems) {
        orderItems = orderItemRepository.saveAll(orderItems);

        SellerOrder sellerOrder = SellerOrder.of(sellerId, orderItems);
        sellerOrderRepository.save(sellerOrder);
    }

    @Transactional(readOnly = true)
    public List<SellerOrderResponse> getAllSellerOrders(Long sellerId) {
        List<SellerOrder> sellerOrders = sellerOrderRepository.findAllBySellerId(sellerId);
        return sellerOrders.stream()
                .map(SellerOrderResponse::of)
                .toList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelSellerOrders(OrderItems orderItems) {
        Set<SellerOrder> sellerOrders = orderItems.getSellerOrders();
        sellerOrders.forEach(SellerOrder::cancel);
        sellerOrderRepository.saveAll(sellerOrders);
    }
}
