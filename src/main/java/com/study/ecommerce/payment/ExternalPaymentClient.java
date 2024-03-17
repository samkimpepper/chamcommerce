package com.study.ecommerce.payment;

import com.study.ecommerce.payment.domain.PaymentStatus;
import com.study.ecommerce.payment.dto.ExternalPaymentResponse;
import com.study.ecommerce.payment.dto.PaymentRequest;

public interface ExternalPaymentClient {
    ExternalPaymentResponse requestPayment(PaymentRequest paymentRequest);
    void cancelPayment(String providerPaymentId);

    PaymentStatus getPaymentStatus(String providerPaymentId);
}
