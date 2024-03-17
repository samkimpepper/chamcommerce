package com.study.ecommerce.payment.dto;

import com.study.ecommerce.payment.domain.Payment;
import com.study.ecommerce.payment.domain.PaymentMethod;
import com.study.ecommerce.payment.dto.ExternalPaymentResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentRequest {
    private Long customerId;
    private int amount;
    private PaymentMethod paymentMethod;

    public Payment toEntity(ExternalPaymentResponse response) {
        return Payment.builder()
                .customerId(customerId)
                .providerPaymentId(response.getProviderPaymentId())
                .status(response.getStatus())
                .amount(amount)
                .paymentMethod(paymentMethod)
                .build();
    }
}
