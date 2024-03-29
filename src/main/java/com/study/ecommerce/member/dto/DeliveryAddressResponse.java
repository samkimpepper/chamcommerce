package com.study.ecommerce.member.dto;

import com.study.ecommerce.member.domain.DeliveryAddress;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DeliveryAddressResponse {
    private Long id;

    private String postCode;

    private String roadAddress;

    private String detailAddress;

    private String memo;

    private String recipientName;

    private String recipientPhoneNumber;

    private boolean main;

    public static List<DeliveryAddressResponse> listOf(List<DeliveryAddress> deliveryAddresses) {
        return deliveryAddresses.stream()
                .map(DeliveryAddressResponse::of)
                .toList();
    }

    public static DeliveryAddressResponse of(DeliveryAddress deliveryAddress) {
        return DeliveryAddressResponse.builder()
                .id(deliveryAddress.getId())
                .postCode(deliveryAddress.getPostCode())
                .roadAddress(deliveryAddress.getRoadAddress())
                .detailAddress(deliveryAddress.getDetailAddress())
                .memo(deliveryAddress.getMemo())
                .recipientName(deliveryAddress.getRecipientName())
                .recipientPhoneNumber(deliveryAddress.getRecipientPhoneNumber())
                .main(deliveryAddress.isMain())
                .build();
    }
}
