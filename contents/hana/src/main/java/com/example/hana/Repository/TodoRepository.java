package com.example.hana.Repository;

import com.example.hana.Entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
//jpa를 상속받고 entity와 entity의 컬럼의 기본값(entity에서 @id) 타입인 long을 지정
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser_Id(Long id);//Todo에서 id를 조회하기 위해 findBy사용
}