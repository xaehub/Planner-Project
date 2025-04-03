package com.example.planner.service;

import com.example.planner.dto.SignResponseDto;
import com.example.planner.entity.User;
import com.example.planner.repository.SignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignService {

    private final SignRepository signRepository;

    public SignResponseDto login(String email, String password) {
        // 입력받은 email, password와 일치하는 Database 조회
        User index = signRepository.findUserByEmailAndPassword(email, password);

        //예외처리

        return new SignResponseDto(index.getId(), index.getUsername(), index.getEmail());
    }
}
