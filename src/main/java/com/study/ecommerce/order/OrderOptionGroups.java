package com.study.ecommerce.order;

import com.study.ecommerce.product.domain.ProductOptionGroup;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Embeddable
public class OrderOptionGroups implements Iterable<OrderOptionGroup> {
    @OneToMany(mappedBy = "order")
    @Builder.Default
    private List<OrderOptionGroup> orderOptionGroups = new ArrayList<>();


    @Override
    public Iterator<OrderOptionGroup> iterator() {
        return orderOptionGroups.iterator();
    }

    public Stream<OrderOptionGroup> stream() {
        return orderOptionGroups.stream();
    }

    public void setOrderOptionGroups(List<OrderOptionGroup> orderOptionGroups) {
        this.orderOptionGroups = orderOptionGroups;
    }

    public void add(OrderOptionGroup orderOptionGroup) {
        orderOptionGroups.add(orderOptionGroup);
    }

    public int size() {
        return orderOptionGroups.size();
    }

    public int getTotalPrice() {
        return orderOptionGroups.stream()
                .mapToInt(OrderOptionGroup::getTotalPrice)
                .sum();
    }

    public int getTotalQuantity() {
        return orderOptionGroups.stream()
                .mapToInt(OrderOptionGroup::getQuantity)
                .sum();
    }

    public int getTotalDeliveryFee() {
        return 0;
    }
}
