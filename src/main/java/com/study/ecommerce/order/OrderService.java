package com.study.ecommerce.order;

import com.study.ecommerce.order.dto.OrderCreateRequest;
import com.study.ecommerce.order.dto.OrderOptionGroupRequest;
import com.study.ecommerce.order.dto.OrderResponse;
import com.study.ecommerce.product.domain.ProductItem;
import com.study.ecommerce.product.domain.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductItemRepository productItemRepository;

    public OrderResponse createOrder(OrderCreateRequest request, Long customerId) {
        List<ProductItem> productItems = productItemRepository
                .findAllById(request.getOrderOptionGroups()
                .stream()
                .map(OrderOptionGroupRequest::getProductOptionGroupId)
                .collect(Collectors.toList()));

        Map<Long, ProductItem> productOptionGroupMap = productItems.stream()
                .collect(Collectors.toMap(ProductItem::getId, Function.identity()));

        List<OrderItem> orderItems = request.getOrderOptionGroups()
                .stream()
                .map(orderOptionGroupRequest -> orderOptionGroupRequest.toEntity(
                        productOptionGroupMap.get(orderOptionGroupRequest.getProductOptionGroupId())
                ))
                .collect(Collectors.toList());

        Order order = Order.of(customerId, orderItems);

        orderRepository.save(order);

        return OrderResponse.of(order);
    }
}
