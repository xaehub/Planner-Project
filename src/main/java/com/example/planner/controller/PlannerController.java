package com.example.planner.controller;

import com.example.planner.dto.CreatePlannerRequestDto;
import com.example.planner.dto.PlannerResponseDto;
import com.example.planner.service.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<PlannerResponseDto>> findAll() {
        List<PlannerResponseDto> plannerResponseDtoList = plannerService.findAll();

        return new ResponseEntity<>(plannerResponseDtoList, HttpStatus.OK);
    }

}
