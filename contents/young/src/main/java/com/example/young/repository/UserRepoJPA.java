package com.example.young.repository;

import com.example.young.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepoJPA extends JpaRepository<User, Long> {
}
