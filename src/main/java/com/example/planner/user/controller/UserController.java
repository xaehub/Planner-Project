package com.example.planner.user.controller;

import com.example.planner.user.dto.SignUpRequestDto;
import com.example.planner.user.dto.SignUpResponseDto;
import com.example.planner.user.dto.UpdateRequestDto;
import com.example.planner.user.dto.UserResponseDto;
import com.example.planner.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 유저가 회원가입 기능을 처리
     * @param requestDto 회원가입 요청에 필요한 유저 정보
     * @return 회원가입 성공하면 유저 정보를 SignUpResponseDto에 담아 반환
     */
    @PostMapping
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {

        // 유저 정보로 회원가입
        SignUpResponseDto signUpResponseDto = userService.signUp(requestDto.getUsername(), requestDto.getEmail(), requestDto.getPassword());

        // 회원가입 성공시 생성된 유저 정보를 반환하고 httpstatus 201 반환
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);

    }

    /**
     * 유저 id로 유저 정보를 조회
     * @param id 유저 id
     * @return 조회한 유저 정보를 UserResponseDto에 담아 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {

        // 해당 유저 조회
        UserResponseDto userResponseDto = userService.findById(id);

        // 조회된 유저 정보를 userResponseDto로 반환
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);

    }

    /**
     * 유저 정보를 업데이트
     * @param id 업데이트할 유저 고유식별자
     * @param requestDto 유저 정보 수정 요청
     * @return httpStatus코드 200 OK 반환
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateRequestDto requestDto
    ) {

        // 유저 정보 업데이트
        userService.updateUser(id, requestDto.getOldEmail(), requestDto.getNewEmail(), requestDto.getOldPassword(), requestDto.getNewPassword());

        // 업데이트 성공하면 ok 반환
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
