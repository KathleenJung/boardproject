package com.example.board.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userid;

    private String pw;

    private String roles;

    protected Member() {
    }

    public static Member createUser(String userId, String pw, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setUserid(userId);
        member.setPw(passwordEncoder.encode(pw));
        member.setRoles("USER");
        return member;
    }
}
