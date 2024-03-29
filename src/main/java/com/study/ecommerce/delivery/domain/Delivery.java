package com.study.ecommerce.delivery.domain;

import com.study.ecommerce.member.domain.DeliveryAddress;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.SellerOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DeliveryStatus status = DeliveryStatus.SHIPPED;

    @ManyToOne
    private DeliveryAddress deliveryAddress;

    @ManyToOne
    private Order order;

    @OneToOne
    private SellerOrder sellerOrder;

    private Long deliveryWorkerId;

    private String trackingNumber;

    private String productNames;

    private LocalDateTime shippedAt;

    private LocalDateTime deliveredAt;

    public static Delivery of(Order order, SellerOrder sellerOrder, String trackingNumber) {
        return Delivery.builder()
                .order(order)
                .sellerOrder(sellerOrder)
                .deliveryAddress(order.getDeliveryAddress())
                .deliveryWorkerId(4L)
                .trackingNumber(trackingNumber)
                .productNames(order.getProductNames())
                .shippedAt(LocalDateTime.now())
                .build();
    }

    public void deliver() {
        this.status = DeliveryStatus.DELIVERED;
        this.deliveredAt = LocalDateTime.now();
    }

    public void start() {
        this.status = DeliveryStatus.DELIVERING;
    }

    public boolean isDelivered() {
        return this.status == DeliveryStatus.DELIVERED;
    }
}
