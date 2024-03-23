package com.study.ecommerce.member.dto;

import com.study.ecommerce.member.DeliveryAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryAddressCreateRequest {
    private String postCode;

    private String roadAddress;

    private String detailAddress;

    private String memo;

    private String recipientName;

    private String recipientPhoneNumber;

    private boolean main;

    public DeliveryAddress toEntity(Long memberId) {
        return DeliveryAddress.builder()
                .postCode(postCode)
                .roadAddress(roadAddress)
                .detailAddress(detailAddress)
                .memo(memo)
                .recipientName(recipientName)
                .recipientPhoneNumber(recipientPhoneNumber)
                .main(main)
                .recipientId(memberId)
                .build();
    }
}
