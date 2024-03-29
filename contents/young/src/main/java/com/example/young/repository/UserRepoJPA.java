package com.example.young.repository;

import com.example.young.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepoJPA extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);  // 회원가입 시 아이디 중복 체크
    boolean existsByName(String name);  // 회원가입 시 이름 중복 체크

    Optional<User> findByUserId(String userId);
}
