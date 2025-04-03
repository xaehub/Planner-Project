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

    /**
     * 클라이언트가 로그인 요청을 하고 이메일과 비밀번호 검증 후 로그인 처리
     * @param dto 이메일, 비밀번호가 담긴 dto
     * @param request http 요청, 세션 정보
     * @return 로그인 성공하면 로그인 정보를 담은 responsedto 반환
     */
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

    /**
     * 클라이언트가 로그아웃 요청하면 현재 세션 종료
     * @param request http 요청, 세션 정보를 다룸
     * @return 로그아웃 성공!! 메시지 반환
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        //로그인 후 세션 가져오기
        HttpSession session = request.getSession(false);

        // 세션이 없으면 로그아웃 실패!! 반환
        if (session == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "로그아웃 실패!!"));
        }

        // 세션 무효화 ( 로그아웃 처리)
        session.invalidate();

        // 로그아웃 성공시 메시지 반환
        return ResponseEntity.ok(Collections.singletonMap("message", "로그아웃 성공!!"));
    }

    /**
     * 로그인할 때 이메일 또는 비밀번호가 다르면 401 예외 처리
     * @return 로그인 실패시 "이메일 또는 비밀번호가 잘못되었습니다"라는 message가 반환
     */
    @ExceptionHandler(CustomExeption.class)
    public ResponseEntity<?> customError() {

        // 이메일 또는 비밀번호가 잘못되었을 때 401 상태 코드와 오류 메시지를 body에서 보여줌
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "이메일 또는 비밀번호가 잘못되었습니다."));
    }

}
