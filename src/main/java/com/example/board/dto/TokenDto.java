package com.example.board.dto;

import lombok.Data;

@Data
public class TokenDto {
    private String token;

    public TokenDto(String jwt) {
        this.token = jwt;
    }
}
