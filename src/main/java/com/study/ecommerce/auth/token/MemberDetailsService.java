package com.study.ecommerce.auth.token;

import com.study.ecommerce.member.domain.Member;
import com.study.ecommerce.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return createMemberDetails(member);
    }

    private MemberDetails createMemberDetails(Member member) {
        return new MemberDetails(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getRoles()
        );
    }

    private User createUser(Member member) {
        return new User(member.getEmail(),
                member.getPassword(),
                member.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList()));
    }
}
