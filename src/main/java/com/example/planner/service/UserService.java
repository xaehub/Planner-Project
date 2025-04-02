package com.example.planner.service;

import com.example.planner.dto.SignUpResponseDto;
import com.example.planner.dto.UserResponseDto;
import com.example.planner.entity.User;
import com.example.planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByIdOrElseThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + "는 없는 아이디입니다."));
    }

    public SignUpResponseDto signUp(String username, String email, String password) {


        User user = new User(username, email, password);

        User savedUser = userRepository.save(user);


        return new SignUpResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    public UserResponseDto findById(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id + "는 존재하지 않는 id입니다.");
        }

        User findUser = optionalUser.get();

        return new UserResponseDto(findUser.getId(), findUser.getUsername(), findUser.getEmail());

    }

    @Transactional
    public void updateUser(Long id, String oldEmail, String newEmail, String oldPassword, String newPassword) {

        User findUser = findByIdOrElseThrow(id);

        if (!findUser.getEmail().equals(oldEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이 일치하지 않습니다.");
        }

        if (!findUser.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        findUser.updateUser(newEmail, newPassword);

    }
}
