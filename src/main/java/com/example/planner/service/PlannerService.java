package com.example.planner.service;

import com.example.planner.dto.PlannerResponseDto;
import com.example.planner.dto.PlannerWithUsernameResponseDto;
import com.example.planner.entity.Planner;
import com.example.planner.entity.User;
import com.example.planner.repository.PlannerRepository;
import com.example.planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final UserRepository userRepository;
    private final PlannerRepository plannerRepository;

    public User findUserByUsernameOrElseThrow(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, username + "은(는) 없는 이름입니다."));
    }

    public Planner findByIdOrElseThrow(Long id) {
        return plannerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + "는 없는 아이디입니다."));
    }


    public PlannerResponseDto save(String title, String contents, String username) {

        User findUsername = findUserByUsernameOrElseThrow(username);

        Planner planner = new Planner(title, contents);
        planner.setUser(findUsername);

        Planner savedPlanner = plannerRepository.save(planner);

        return new PlannerResponseDto(savedPlanner.getId(), savedPlanner.getTitle(), savedPlanner.getContents());
    }

    public List<PlannerResponseDto> findAll() {

        return plannerRepository.findAll().stream().map(PlannerResponseDto::toDto).toList();
    }

    public PlannerWithUsernameResponseDto findById(Long id) {

        Planner findPlanner = findByIdOrElseThrow(id);
        User username = findPlanner.getUser();

        return new PlannerWithUsernameResponseDto(username.getUsername(), findPlanner.getTitle(), findPlanner.getContents());
    }
}
