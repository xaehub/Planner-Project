package com.example.planner.sign.repository;

import com.example.planner.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignRepository extends JpaRepository<User, Long> {

    User findUserByEmailAndPassword(String email, String password);

}
