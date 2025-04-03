package com.example.planner.planner.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePlannerRequestDto {

    @Size(max = 10, message = "수정할 때도 제목은 10글자 제한입니다.")
    private final String newTitle;

    private final String newContents;

    public UpdatePlannerRequestDto(String newTitle, String newContents) {
        this.newTitle = newTitle;
        this.newContents = newContents;
    }
}
