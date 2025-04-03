package com.example.planner.planner.dto;

import lombok.Getter;

@Getter
public class CreatePlannerRequestDto {

    private final String title;

    private final String contents;

    private final String username;

    public CreatePlannerRequestDto(String title, String contents, String username) {
        this.title = title;
        this.contents = contents;
        this.username = username;
    }
}
