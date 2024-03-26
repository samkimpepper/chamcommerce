package com.study.ecommerce.order.domain;

import com.study.ecommerce.delivery.domain.Delivery;
import com.study.ecommerce.member.DeliveryAddress;
import com.study.ecommerce.order.DeliveryFeeCalculator;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "orders")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private LocalDateTime orderedAt;

    private LocalDateTime cancelledAt;

    private String productNames;

    @Setter
    private int deliveryFee;

    private int totalDeliveryCount;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.ORDERED;

    @Embedded
    @Builder.Default
    private OrderItems orderItems = new OrderItems();

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne
    private DeliveryAddress deliveryAddress;

    @OneToMany(mappedBy = "order")
    @Builder.Default
    private List<Delivery> deliveries = new ArrayList<>();

    public int getTotalPrice() {
        return orderItems.getTotalPrice();
    }

    public int getTotalQuantity() {
        return orderItems.getTotalQuantity();
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems.setOrderItems(orderItems);
    }

    public static Order of(Long customerId, List<OrderItem> orderItems, String paymentMethod, DeliveryAddress deliveryAddress) {
        Order order = Order.builder()
                .customerId(customerId)
                .productNames(orderItems.get(0).getProductName() + " 외 " + (orderItems.size() - 1) + "건")
                .deliveryAddress(deliveryAddress)
                .paymentMethod(PaymentMethod.valueOf(paymentMethod))
                .status((paymentMethod.equals(PaymentMethod.DEFERRED.name())) ? OrderStatus.WAITING_FOR_PAYMENT : OrderStatus.ORDERED)
                .orderedAt(LocalDateTime.now())
                .build();

        order.setOrderItems(orderItems);
        order.setDeliveryFee(DeliveryFeeCalculator.calculate(order.getOrderItems()));

        order.totalDeliveryCount = (int) orderItems.stream()
                .collect(Collectors.groupingBy(orderItem -> orderItem.getProductItem().getProduct().getSellerId()))
                .size();

        return order;
    }

    public void cancel() {
        if (!status.isOrdered()) {
            throw new IllegalStateException("이미 배송이 시작된 주문은 취소가 불가능합니다.");
        }

        this.status = OrderStatus.CANCELLED;
        this.cancelledAt = LocalDateTime.now();
    }

    public void complete() {
        if (!status.isDelivered()) {
            throw new IllegalStateException("배송이 완료되지 않은 주문은 완료가 불가능합니다.");
        }

        this.status = OrderStatus.DELIVERED;
    }

    public void registerDelivery(Delivery delivery) {
        this.deliveries.add(delivery);
        this.status = OrderStatus.DELIVERING;
    }

    public void completeDelivery() {
        if (deliveries.size() == totalDeliveryCount && deliveries.stream().allMatch(Delivery::isDelivered)) {
            this.status = OrderStatus.DELIVERED;
        }
    }
}
