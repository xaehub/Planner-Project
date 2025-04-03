package com.example.planner.planner.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreatePlannerRequestDto {

    @Size(max = 10, message = "제목은 10글자 제한입니다.")
    private final String title;

    private final String contents;

    private final String username;

    public CreatePlannerRequestDto(String title, String contents, String username) {
        this.title = title;
        this.contents = contents;
        this.username = username;
    }
}
