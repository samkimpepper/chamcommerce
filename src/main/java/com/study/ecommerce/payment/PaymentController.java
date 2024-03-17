package com.study.ecommerce.payment;

import com.study.ecommerce.payment.dto.PaymentRequest;
import com.study.ecommerce.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> createPayment(PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);

        return ResponseEntity.ok(response);
    }
}
