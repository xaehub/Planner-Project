package com.example.planner.controller;

import com.example.planner.dto.CreatePlannerRequestDto;
import com.example.planner.dto.PlannerResponseDto;
import com.example.planner.service.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;

    @PostMapping
    public ResponseEntity<PlannerResponseDto> save(@RequestBody CreatePlannerRequestDto requestDto) {

        PlannerResponseDto plannerResponseDto = plannerService.save(requestDto.getTitle(), requestDto.getContents(), requestDto.getUsername());

        return new ResponseEntity<>(plannerResponseDto, HttpStatus.CREATED);
    }

}
