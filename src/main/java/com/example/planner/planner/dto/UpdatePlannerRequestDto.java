package com.example.planner.planner.dto;

import lombok.Getter;

@Getter
public class UpdatePlannerRequestDto {

    private final String newTitle;

    private final String newContents;

    public UpdatePlannerRequestDto(String newTitle, String newContents) {
        this.newTitle = newTitle;
        this.newContents = newContents;
    }
}
