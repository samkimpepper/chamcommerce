package com.study.ecommerce.payment;

import com.study.ecommerce.notification.Notification;
import com.study.ecommerce.notification.NotificationType;
import com.study.ecommerce.payment.domain.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCompletedEvent {
    private Payment payment;

    public Notification toNotification() {
        return Notification.builder()
                .content(payment.getAmount() + "원 결제가 완료되었습니다.")
                .link("")
                .type(NotificationType.PAYMENT_COMPLETED)
                .receiverId(payment.getCustomerId())
                .createdAt(payment.getPaidAt())
                .build();
    }
}
