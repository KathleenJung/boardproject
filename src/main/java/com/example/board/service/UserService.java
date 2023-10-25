package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.dto.UserDto;

import java.util.Optional;

public interface UserService {
    User signup(UserDto userDto);

    Optional<User> getUserWithAuthorities(String username);
}
