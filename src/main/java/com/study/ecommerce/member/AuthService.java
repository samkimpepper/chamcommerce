package com.study.ecommerce.member;

import com.study.ecommerce.auth.token.JwtTokenProvider;
import com.study.ecommerce.member.domain.Member;
import com.study.ecommerce.member.domain.Role;
import com.study.ecommerce.member.dto.LoginRequest;
import com.study.ecommerce.member.dto.SignupRequest;
import com.study.ecommerce.member.dto.TokenResponse;
import com.study.ecommerce.member.event.MemberSignedUpEvent;
import com.study.ecommerce.member.exception.InvalidPasswordException;
import com.study.ecommerce.member.exception.MemberAlreadyExistsException;
import com.study.ecommerce.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ApplicationEventPublisher eventPublisher;

    public Member signUp(SignupRequest signupRequest) {
        if (memberRepository.existsByEmail(signupRequest.getEmail())) {
            throw new MemberAlreadyExistsException();
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        Member member = Member.builder()
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .roles(Set.of(Role.valueOf(signupRequest.getRole())))
                .build();

        memberRepository.save(member);

        eventPublisher.publishEvent(new MemberSignedUpEvent(member.getId(), Role.valueOf(signupRequest.getRole())));

        return member;
    }

    public TokenResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(MemberNotFoundException::new);
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new InvalidPasswordException();
        }

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(request.toAuthentication());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return new TokenResponse(token);
    }
}
