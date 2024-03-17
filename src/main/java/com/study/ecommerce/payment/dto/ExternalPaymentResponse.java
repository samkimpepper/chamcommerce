package com.study.ecommerce.payment.dto;

import com.study.ecommerce.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExternalPaymentResponse {
    private String providerPaymentId;
    private PaymentStatus status;
}
