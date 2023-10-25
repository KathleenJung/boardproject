package com.example.board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @Builder
    public User(Long id, String username, String password, String name, Set<Authority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.authorities = authorities;
    }

    protected User() {
    }

    public User(String username, String name, Set<Authority> authorities) {
        this.username = username;
        this.name = name;
        this.authorities = authorities;
    }

    public User(Long id, String username, String name, Set<Authority> authorities) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.authorities = authorities;
    }

    public static User createUser(String username, String pwd, String name, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(pwd));
        user.setName(name);
        return user;
    }
}
