package com.example.planner.sign.service;

import com.example.planner.sign.exception.CustomExeption;
import com.example.planner.sign.dto.SignResponseDto;
import com.example.planner.user.entity.User;
import com.example.planner.sign.repository.SignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignService {

    private final SignRepository signRepository;

    /**
     * 로그인 기능을 처리
     * @param email 유저 이메일
     * @param password 유저 비밀번호
     * @return 로그인 성공하면 user 정보를 담은 SignResponseDto 반환
     */
    public SignResponseDto login(String email, String password) {
        // 입력받은 email, password와 일치하는 Database 조회
        User index = signRepository.findUserByEmailAndPassword(email, password);

        // 해당 사용자가 존재하지 않으면 로그인 실패 예외 처리
        if(index == null) {
            // 커스텀 예외처리
            throw new CustomExeption();
        }

        return new SignResponseDto(index.getId(), index.getUsername(), index.getEmail());
    }
}
