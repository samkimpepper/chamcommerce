package com.study.ecommerce.order;

import com.study.ecommerce.order.dto.OrderCreateRequest;
import com.study.ecommerce.order.dto.OrderOptionGroupRequest;
import com.study.ecommerce.order.dto.OrderResponse;
import com.study.ecommerce.product.domain.ProductOptionGroup;
import com.study.ecommerce.product.domain.ProductOptionGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderOptionGroupRepository orderOptionGroupRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;

    public OrderResponse createOrder(OrderCreateRequest request, Long customerId) {
        List<ProductOptionGroup> productOptionGroups = productOptionGroupRepository
                .findAllById(request.getOrderOptionGroups()
                .stream()
                .map(OrderOptionGroupRequest::getProductOptionGroupId)
                .collect(Collectors.toList()));

        Map<Long, ProductOptionGroup> productOptionGroupMap = productOptionGroups.stream()
                .collect(Collectors.toMap(ProductOptionGroup::getId, Function.identity()));

        List<OrderOptionGroup> orderOptionGroups = request.getOrderOptionGroups()
                .stream()
                .map(orderOptionGroupRequest -> orderOptionGroupRequest.toEntity(
                        productOptionGroupMap.get(orderOptionGroupRequest.getProductOptionGroupId())
                ))
                .collect(Collectors.toList());

        Order order = Order.of(customerId, orderOptionGroups);

        orderRepository.save(order);

        return OrderResponse.of(order);
    }
}
