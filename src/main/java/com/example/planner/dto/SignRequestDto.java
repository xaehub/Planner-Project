package com.example.planner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignRequestDto {

    @NotBlank
    private final String email;

    @NotNull
    private final String password;
}
