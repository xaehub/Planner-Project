package com.example.planner.service;

import com.example.planner.dto.SignUpResponseDto;
import com.example.planner.entity.User;
import com.example.planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignUpResponseDto signUp(String username, String email, String password) {


        User user = new User(username, email, password);

        User savedUser = userRepository.save(user);


        return new SignUpResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }



}
