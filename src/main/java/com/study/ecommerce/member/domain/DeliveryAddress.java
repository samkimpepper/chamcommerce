package com.study.ecommerce.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postCode;

    private String roadAddress;

    private String detailAddress;

    private String memo;

    private String recipientName;

    private String recipientPhoneNumber;

    private boolean main;

    private Long recipientId;

    public boolean isMain() {
        return main;
    }

    public void unmarkMain() {
        this.main = false;
    }
}
