package com.example.planner.planner.service;

import com.example.planner.planner.dto.PlannerResponseDto;
import com.example.planner.planner.dto.PlannerWithUsernameResponseDto;
import com.example.planner.planner.entity.Planner;
import com.example.planner.user.entity.User;
import com.example.planner.planner.repository.PlannerRepository;
import com.example.planner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final UserRepository userRepository;
    private final PlannerRepository plannerRepository;

    /**
     * 사용자 이름으로 user 찾기, 없으면 예외처리
     * @param username 유저이름
     * @return user 반환
     */
    public User findUserByUsernameOrElseThrow(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, username + "은(는) 없는 이름입니다."));
    }

    /**
     * 사용자 id로 user 찾기 없으면 예외처리
     * @param id user 고유식별자
     * @return user 반환
     */
    public Planner findByIdOrElseThrow(Long id) {
        return plannerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + "는 없는 아이디입니다."));
    }


    /**
     * 일정을 생성하고 저장 후에 저장된 일정을 dto 형태로 response
     * @param title 일정 제목
     * @param contents 일정 내용
     * @param username 사용자 이름
     * @return 저장된 일정 PlannerResponseDto 형태로 반환
     */
    public PlannerResponseDto save(String title, String contents, String username) {

        // 사용자 정보 추가
        User findUsername = findUserByUsernameOrElseThrow(username);

        // 객체 생성
        Planner planner = new Planner(title, contents);
        planner.setUser(findUsername);

        // 저장된 객체 반환
        Planner savedPlanner = plannerRepository.save(planner);

        // 반환받은 객체 DTO 형태로 반환
        return new PlannerResponseDto(savedPlanner.getId(), savedPlanner.getTitle(), savedPlanner.getContents());
    }

    /**
     * 전체 일정 조회하고 DTO 형태로 반환
     * @return 전체 일정 반환
     */
    public List<PlannerResponseDto> findAll() {

        // 모든 Planner 객체를 조회하고, 각 객체를 dto로 변환하여 리스트로 반환
        return plannerRepository.findAll().stream().map(PlannerResponseDto::toDto).toList();
    }

    /**
     * id로 일정 조회하고 id에 맞는 username도 함께 반환
     * @param id 일정 고유식별자
     * @return 사용자명과 제목, 내용을 함께 반환
     */
    public PlannerWithUsernameResponseDto findById(Long id) {

        Planner findPlanner = findByIdOrElseThrow(id);
        User username = findPlanner.getUser();

        // 일정제목, 내용, 사용자명을 dto로 변환하여 반환
        return new PlannerWithUsernameResponseDto(username.getUsername(), findPlanner.getTitle(), findPlanner.getContents());
    }

    /**
     * id로 일정 삭제
     * @param id 일정 고유식별자
     */
    public void delete(Long id) {
        Planner findPlanner = findByIdOrElseThrow(id);

        plannerRepository.delete(findPlanner);
    }

    /**
     * id로 제목과 내용 수정
     * @param id id 고유식별자
     * @param newTitle 새로운 제목
     * @param newContents 새로운 내용
     */
    @Transactional
    public void updatePlanner(Long id, String newTitle, String newContents) {
        Planner findPlanner = findByIdOrElseThrow(id);

        findPlanner.updatePlanner(newTitle,newContents);
    }
}
