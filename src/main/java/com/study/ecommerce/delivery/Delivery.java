package com.study.ecommerce.delivery;

import com.study.ecommerce.member.DeliveryAddress;
import com.study.ecommerce.order.domain.Order;
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

    @OneToOne
    private DeliveryAddress deliveryAddress;

    @ManyToOne
    private Order order;

    private Long deliveryWorkerId;

    private LocalDateTime shippedAt;

    private LocalDateTime deliveredAt;

    public void deliver() {
        this.status = DeliveryStatus.DELIVERED;
        this.deliveredAt = LocalDateTime.now();
    }


}
