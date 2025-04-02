package com.example.planner.repository;

import com.example.planner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//    default User findByIdOrElseThrow(Long id) {
//        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + "는 없는 아이디입니다."));
//    }

}
