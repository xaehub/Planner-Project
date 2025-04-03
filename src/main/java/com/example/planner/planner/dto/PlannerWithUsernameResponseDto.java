package com.example.planner.planner.dto;

import lombok.Getter;

@Getter
public class PlannerWithUsernameResponseDto {

    private final String username;

    private final String title;

    private final String contents;

    public PlannerWithUsernameResponseDto(String username, String title, String contents) {
        this.username = username;
        this.title = title;
        this.contents = contents;
    }
}
