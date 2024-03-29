package com.study.ecommerce.member.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Builder.Default
    private long points = 0;

    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MemberGrade grade = MemberGrade.WHITE;

    public void earnPoints(long points) {
        this.points += points;
    }

    public void usePoints(long points) {
        if (this.points < points) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }
        this.points -= points;
    }
}
