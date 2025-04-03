package com.example.planner.dto;

import com.example.planner.entity.Planner;
import lombok.Getter;

@Getter
public class PlannerResponseDto {

    private final Long id;

    private final String title;

    private final String contents;

    public PlannerResponseDto(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public static PlannerResponseDto toDto(Planner planner) {
        return new PlannerResponseDto(planner.getId(), planner.getTitle(), planner.getContents());
    }
}
