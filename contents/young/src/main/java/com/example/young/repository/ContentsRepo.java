package com.example.young.repository;

import com.example.young.entity.Contents;

import java.util.List;
import java.util.Optional;

public interface ContentsRepo {
    Contents save(Contents contents);
    Optional<Contents> findbyID(Long ID);
    Optional<Contents> findByloginId(String loginId);
    // 리포지토리(저장소)에는 외래키 어노테이션을 해줬는데 엔티티에도 추가 작성을 했음. 뭐가 맞는건지..???
    List<Contents> findAll();
}
