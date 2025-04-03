package com.example.planner.sign.dto;

import lombok.Getter;

@Getter
public class SignResponseDto {

    private final Long id;

    private final String email;

    private final String username;

    public SignResponseDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
