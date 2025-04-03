package com.example.planner.sign.service;

import com.example.planner.global.exception.CustomExeption;
import com.example.planner.sign.dto.SignResponseDto;
import com.example.planner.user.entity.User;
import com.example.planner.sign.repository.SignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignService {

    private final SignRepository signRepository;

    public SignResponseDto login(String email, String password) {
        // 입력받은 email, password와 일치하는 Database 조회
        User index = signRepository.findUserByEmailAndPassword(email, password);

        if(index == null) {
            throw new CustomExeption();
        }

        return new SignResponseDto(index.getId(), index.getUsername(), index.getEmail());
    }
}
