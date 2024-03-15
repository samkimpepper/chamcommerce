package com.study.ecommerce.order;

import com.study.ecommerce.member.Member;
import com.study.ecommerce.product.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeliveryFeeCalculator {
    public static final int FREE_DELIVERY_THRESHOLD = 30_000;
    public static final int BASE_DELIVERY_FEE = 3_000;

    public static int calculate(OrderOptionGroups orderOptionGroups) {
        int totalDeliveryFee = 0;

        Map<Long, List<OrderOptionGroup>> sellerOrderOptionGroupMap = orderOptionGroups.stream()
                .collect(Collectors.groupingBy(orderOptionGroup -> orderOptionGroup.getProductOptionGroup().getProduct().getSellerId()));

        for (List<OrderOptionGroup> orderOptionGroupsBySeller : sellerOrderOptionGroupMap.values()) {
            int sellerTotalPrice = orderOptionGroupsBySeller.stream()
                    .mapToInt(OrderOptionGroup::getTotalPrice)
                    .sum();

            if (sellerTotalPrice < FREE_DELIVERY_THRESHOLD) {
                totalDeliveryFee += BASE_DELIVERY_FEE;
            }
        }

        return totalDeliveryFee;
    }
}
