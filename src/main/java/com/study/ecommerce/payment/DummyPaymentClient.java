package com.study.ecommerce.payment;

import com.study.ecommerce.payment.domain.PaymentStatus;
import com.study.ecommerce.payment.dto.ExternalPaymentResponse;
import com.study.ecommerce.payment.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DummyPaymentClient implements ExternalPaymentClient {
    private Map<String, PaymentStatus> paymentStatusMap = new HashMap<>();

    @Override
    public ExternalPaymentResponse requestPayment(PaymentRequest paymentRequest) {
        String providerPaymentId = "dummyProviderPaymentId";
        PaymentStatus status = PaymentStatus.PAYMENT_COMPLETED;

        paymentStatusMap.put(providerPaymentId, status);

        return new ExternalPaymentResponse(providerPaymentId, status);
    }

    @Override
    public void cancelPayment(String providerPaymentId) {
        // do nothing
        paymentStatusMap.put(providerPaymentId, PaymentStatus.PAYMENT_FAILED);
    }

    @Override
    public PaymentStatus getPaymentStatus(String providerPaymentId) {
        return paymentStatusMap.get(providerPaymentId);
    }
}
