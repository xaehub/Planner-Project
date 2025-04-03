package com.example.planner.controller;

import com.example.planner.common.Const;
import com.example.planner.dto.SignRequestDto;
import com.example.planner.dto.SignResponseDto;
import com.example.planner.service.SignService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


        // 세션 생성 및 로그인 정보 저장
        HttpSession session = request.getSession(true);
        session.setAttribute(Const.LOGIN_USER, responseDto);

        // 로그인 성공 응답
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Logout successful"));
    }
}
