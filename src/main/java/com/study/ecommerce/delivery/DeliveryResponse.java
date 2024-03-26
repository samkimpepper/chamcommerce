package com.study.ecommerce.delivery;

import com.study.ecommerce.delivery.domain.Delivery;
import com.study.ecommerce.member.dto.DeliveryAddressResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryResponse {
    private Long id;
    private Long orderId;
    private String status;
    private DeliveryAddressResponse deliveryAddress;
    private String trackingNumber;
    private String shippedAt;
    private String deliveredAt;

    public static DeliveryResponse of(Delivery delivery) {
        return DeliveryResponse.builder()
                .id(delivery.getId())
                .orderId(delivery.getOrder().getId())
                .status(delivery.getStatus().name())
                .deliveryAddress(DeliveryAddressResponse.of(delivery.getDeliveryAddress()))
                .trackingNumber(delivery.getTrackingNumber())
                .shippedAt(delivery.getShippedAt().toString())
                .deliveredAt((delivery.getDeliveredAt() == null) ? null : delivery.getDeliveredAt().toString())
                .build();
    }
}
