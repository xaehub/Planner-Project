package com.example.planner.repository;

import com.example.planner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

//    default User findUserByUsernameOrElseThrow(String username) {
//        return findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, username + "은(는) 없는 이름입니다."));
//    }

//    default User findByIdOrElseThrow(Long id) {
//        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + "는 없는 아이디입니다."));
//    }

}
