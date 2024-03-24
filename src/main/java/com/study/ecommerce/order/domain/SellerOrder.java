package com.study.ecommerce.order.domain;

import com.study.ecommerce.delivery.Delivery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;

    @Embedded
    @Builder.Default
    private SellerOrderItems orderItems = new SellerOrderItems();

    @OneToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SellerOrderStatus status = SellerOrderStatus.PENDING;

    private LocalDateTime orderedAt;

    public int getTotalPrice() {
        return orderItems.getTotalPrice();
    }

    public static SellerOrder of(Long sellerId, List<OrderItem> orderItems) {
        SellerOrder sellerOrder = SellerOrder.builder()
                .sellerId(sellerId)
                .orderedAt(LocalDateTime.now())
                .build();

        sellerOrder.orderItems.setOrderItems(orderItems);
        orderItems.forEach(orderItem -> orderItem.setSellerOrder(sellerOrder));
        return sellerOrder;
    }

    public void cancel() {
        this.status = SellerOrderStatus.CANCELLED;
    }
}
