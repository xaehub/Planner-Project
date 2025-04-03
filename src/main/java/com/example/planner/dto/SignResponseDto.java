package com.example.planner.dto;

import lombok.Getter;

@Getter
public class SignResponseDto {

    private final Long id;

    private final String email;

    private final String username;

    public SignResponseDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
