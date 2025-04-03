package com.example.planner.user.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @NotEmpty(message = "이름은 필수 입력 값입니다.")
    @Size(max = 4, message = "이름은 4글자 제한입니다.")
    private final String username;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9]+@google\\.com$", message = "유효한 이메일 형식이 아닙니다.")
    private final String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private final String password;

    public SignUpRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
