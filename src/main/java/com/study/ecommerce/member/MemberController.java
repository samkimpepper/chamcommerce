package com.study.ecommerce.member;

import com.study.ecommerce.auth.token.MemberDetails;
import com.study.ecommerce.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final DeliveryAddressService deliveryAddressService;

    @PostMapping("/member/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignupRequest signupRequest) {
        Member member = memberService.signUp(signupRequest);
        return ResponseEntity.created(URI.create("/members/" + member.getId())).build();
    }

    @PostMapping("/member/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(memberService.login(loginRequest));
    }

    @PostMapping("/member/update/delivery-address")
    public ResponseEntity<DeliveryAddressResponse> createDeliveryAddress(
            @RequestBody DeliveryAddressCreateRequest request,
            @AuthenticationPrincipal MemberDetails member) {
        DeliveryAddressResponse deliveryAddress = deliveryAddressService.createDeliveryAddress(member.getId(), request);
        return ResponseEntity.created(URI.create("/member/delivery-addresses/"))
                .body(deliveryAddress);
    }

    @GetMapping("/member/delivery-addresses")
    public ResponseEntity<List<DeliveryAddressResponse>> showDeliveryAddresses(
            @AuthenticationPrincipal MemberDetails member) {
        List<DeliveryAddressResponse> deliveryAddresses = deliveryAddressService.getDeliveryAddresses(member.getId());
        return ResponseEntity.ok(deliveryAddresses);
    }

}
