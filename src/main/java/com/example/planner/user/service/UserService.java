package com.example.planner.user.service;

import com.example.planner.user.dto.SignUpResponseDto;
import com.example.planner.user.dto.UserResponseDto;
import com.example.planner.user.entity.User;
import com.example.planner.user.repository.UserRepository;
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

    /**
     * id 유저 정보를 조회하고 존재하지 않으면 예외 처리
     * @param id 유저 고유식별자
     * @return 유저 정보
     */
    public User findByIdOrElseThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + "는 없는 아이디입니다."));
    }

    /**
     * 유저 회원가입 처리
     * @param username 유저 이름
     * @param email 유저 이메일
     * @param password 유저 비밀번호
     * @return 회원가입한 유저 정보를 담은 dto 반환
     */
    public SignUpResponseDto signUp(String username, String email, String password) {

        User user = new User(username, email, password);

        // 유저 저장
        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    /**
     * id로 유저 정보 조회
     * @param id 유저 고유식별자
     * @return 유저 정보를 담은 dto 반환
     */
    public UserResponseDto findById(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        // 유저가 없으면 예외 처리
        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id + "는 존재하지 않는 id입니다.");
        }

        User findUser = optionalUser.get();

        return new UserResponseDto(findUser.getId(), findUser.getUsername(), findUser.getEmail());

    }

    /**
     * 유저 이메일과 비밀번호를 검증하고 정보를 업데이트
     * @param id 유저 고유식별자
     * @param oldEmail 기존 이메일
     * @param newEmail 새 이메일
     * @param oldPassword 기존 비밀번호
     * @param newPassword 새 비밀번호
     */
    @Transactional
    public void updateUser(Long id, String oldEmail, String newEmail, String oldPassword, String newPassword) {

        User findUser = findByIdOrElseThrow(id);

        // 기존 이메일과 비밀번호가 일치하는지 확인
        if (!findUser.getEmail().equals(oldEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이 일치하지 않습니다.");
        }

        if (!findUser.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 이메일 검증이 끝나면 업데이트
        findUser.updateUser(newEmail, newPassword);

    }
}
