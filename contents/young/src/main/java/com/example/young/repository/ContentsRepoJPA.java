package com.example.young.repository;

import com.example.young.entity.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsRepoJPA extends JpaRepository<Contents, Long> {
}
