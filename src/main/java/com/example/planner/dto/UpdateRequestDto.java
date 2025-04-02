package com.example.planner.dto;


import lombok.Getter;

@Getter
public class UpdateRequestDto {

    private final String oldEmail;

    private final String newEmail;

    private final String oldPassword;

    private final String newPassword;

    public UpdateRequestDto(String oldEmail, String newEmail, String oldPassword, String newPassword) {
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
