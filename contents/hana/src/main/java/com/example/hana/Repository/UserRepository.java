package com.example.hana.Repository;

import com.example.hana.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
//jpa를 상속받고 entity와 entity의 컬럼의 기본값(entity에서 @id) 타입인 long을 지정
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

}
