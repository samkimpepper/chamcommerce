package com.study.ecommerce.payment;

import com.study.ecommerce.payment.domain.Payment;
import com.study.ecommerce.payment.dto.ExternalPaymentResponse;
import com.study.ecommerce.payment.dto.PaymentRequest;
import com.study.ecommerce.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ExternalPaymentClient paymentClient;
    private final ApplicationEventPublisher eventPublisher;

    public PaymentResponse createPayment(PaymentRequest request) {
        ExternalPaymentResponse response = paymentClient.requestPayment(request);

        Payment payment = request.toEntity(response);

        paymentRepository.save(payment);
        eventPublisher.publishEvent(new PaymentCompletedEvent(payment));

        return new PaymentResponse(payment.getId(), payment.getStatus().name());
    }
}
