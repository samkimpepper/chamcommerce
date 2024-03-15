package com.study.ecommerce.member;

import com.study.ecommerce.member.dto.LoginRequest;
import com.study.ecommerce.member.dto.SignupRequest;
import com.study.ecommerce.member.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignupRequest signupRequest) {
        Member member = memberService.signUp(signupRequest);
        return ResponseEntity.created(URI.create("/members/" + member.getId())).build();
    }

    @PostMapping("/member/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(memberService.login(loginRequest));
    }

}
