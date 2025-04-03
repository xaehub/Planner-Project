package com.example.planner.sign.controller;

import com.example.planner.global.common.Const;
import com.example.planner.global.exception.CustomExeption;
import com.example.planner.sign.dto.SignRequestDto;
import com.example.planner.sign.dto.SignResponseDto;
import com.example.planner.sign.service.SignService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final SignService signService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody SignRequestDto dto,
            HttpServletRequest request
    ) {
        // 로그인 시도
        SignResponseDto responseDto = signService.login(dto.getEmail(), dto.getPassword());


        // 세션 생성
        HttpSession session = request.getSession(true);

        //로그인 정보 세션에 저장
        session.setAttribute("sessionKey", responseDto.getId());
        session.setAttribute(Const.LOGIN_USER, responseDto);

        // 로그인 성공하면 responseDto형태로 응답
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        //로그인 후 세션 가져오기
        HttpSession session = request.getSession(false);

        if (session == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "로그아웃 실패!!"));
        }

        //
        session.invalidate();

        return ResponseEntity.ok(Collections.singletonMap("message", "로그아웃 성공!!"));
    }

    @ExceptionHandler(CustomExeption.class)
    public ResponseEntity<?> customError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "이메일 또는 비밀번호가 잘못되었습니다."));
    }

}
