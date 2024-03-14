package com.study.ecommerce.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequest {
    private String email;
    private String password;
    private String role;
}
