package com.example.planner.sign.service;

import com.example.planner.sign.exception.CustomExeption;
import com.example.planner.sign.dto.SignResponseDto;
import com.example.planner.user.entity.User;
import com.example.planner.sign.repository.SignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.planner.global.config.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class SignService {

    private final SignRepository signRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 기능을 처리
     * @param email 유저 이메일
     * @param password 유저 비밀번호
     * @return 로그인 성공하면 user 정보를 담은 SignResponseDto 반환
     */
    public SignResponseDto login(String email, String password) {

        User user = signRepository.findUserByEmail(email);

        // 해당 사용자가 없으면 로그인 실패 예외 처리
        if (user == null) {
            throw new CustomExeption();
        }

        // 암호화된 비밀번호 비교
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomExeption();
        }

        return new SignResponseDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
