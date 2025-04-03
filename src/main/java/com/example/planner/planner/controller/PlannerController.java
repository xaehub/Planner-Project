package com.example.planner.planner.controller;

import com.example.planner.planner.dto.CreatePlannerRequestDto;
import com.example.planner.planner.dto.PlannerResponseDto;
import com.example.planner.planner.dto.PlannerWithUsernameResponseDto;
import com.example.planner.planner.dto.UpdatePlannerRequestDto;
import com.example.planner.planner.service.PlannerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;

    /**
     * 일정 생성
     * @param requestDto (제목, 내용, 사용자명)
     * @return 생성된 일정 정보를 plannerResponseDto형태로 담아 반환
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CreatePlannerRequestDto requestDto, BindingResult bindingResult) {

        // 유효성 검사 실패 시 처리
        if (bindingResult.hasErrors()) {
            // 오류 메시지 리스트로 변환하여 응답
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errorMessages); // 400 Bad Request와 함께 오류 메시지 반환
        }

        // 일정 생성
        PlannerResponseDto plannerResponseDto = plannerService.save(requestDto.getTitle(), requestDto.getContents(), requestDto.getUsername());

        // 정상적으로 생성된 경우 ResponseEntity<PlannerResponseDto> 반환
        return new ResponseEntity<>(plannerResponseDto, HttpStatus.CREATED);
    }

    /**
     * 전체 일정 조회
     * @return 전체 일정 정보를 plannerResponseDtoList형태로 담아 반환
     */
    @GetMapping
    public ResponseEntity<List<PlannerResponseDto>> findAll() {

        // 전체 일정 조회 plannerService에있는 findAll() 호출
        List<PlannerResponseDto> plannerResponseDtoList = plannerService.findAll();

        return new ResponseEntity<>(plannerResponseDtoList, HttpStatus.OK);
    }

    /**
     * 특정 일정 조회
     * @param id 일정의 고유식별자
     * @return 특정 일정의 정보를 PlannerWithUsernameResponseDto에 담아 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlannerWithUsernameResponseDto>findById(@PathVariable Long id) {

        // 특정 일정 조회
        PlannerWithUsernameResponseDto plannerWithUsernameResponseDto = plannerService.findById(id);

        return new ResponseEntity<>(plannerWithUsernameResponseDto, HttpStatus.OK);
    }

    /**
     * 일정 수정
     * @param id 수정할 일정 고유식별자
     * @param requestDto 수정할 내용
     * @return 수정 완료되면 HttpStatus코드 ok 반환
     */
    @PatchMapping("{id}")
    public ResponseEntity<Void> updatePlanner (
            @PathVariable Long id,
            @RequestBody UpdatePlannerRequestDto requestDto
    ) {
        plannerService.updatePlanner(id, requestDto.getNewTitle(), requestDto.getNewContents());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * 일정 삭제
     * @param id 삭제할 일정 고유식별자
     * @return 정상 삭제되면 HttpStatus코드 OK반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        plannerService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
