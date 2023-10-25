package com.example.board.service;

import com.example.board.domain.Authority;
import com.example.board.domain.User;
import com.example.board.dto.UserDto;
import com.example.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User signup(UserDto userDto) {

        try {
            if (userRepository.findOneWithAuthoritiesByUsername(userDto.getName()).orElse(null) != null) {
                throw new RuntimeException("이미 가입되어 있는 유저입니다.");
            }
            Authority authority = Authority.builder()
                    .authorityName("ROLE_USER")
                    .build();

            User user = User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .name(userDto.getName())
                    .authorities(Collections.singleton(authority))
                    .build();

            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("사용자 가입 실패: " + e.getMessage());
        }
    }

    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }
}
