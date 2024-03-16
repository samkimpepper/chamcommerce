package com.study.ecommerce.order;

import com.study.ecommerce.order.domain.OrderItem;
import com.study.ecommerce.order.domain.OrderItems;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeliveryFeeCalculator {
    public static final int FREE_DELIVERY_THRESHOLD = 50_000;
    public static final int BASE_DELIVERY_FEE = 3_000;

    public static int calculate(OrderItems orderItems) {
        int totalDeliveryFee = 0;

        Map<Long, List<OrderItem>> sellerOrderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderOptionGroup -> orderOptionGroup.getProductItem().getProduct().getSellerId()));

        for (List<OrderItem> orderOptionGroupsBySeller : sellerOrderItemMap.values()) {
            int sellerTotalPrice = orderOptionGroupsBySeller.stream()
                    .mapToInt(OrderItem::getTotalPrice)
                    .sum();

            if (sellerTotalPrice < FREE_DELIVERY_THRESHOLD) {
                totalDeliveryFee += BASE_DELIVERY_FEE;
            }
        }

        return totalDeliveryFee;
    }
}
