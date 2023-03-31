package com.example.hana.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)//접근 레벨 protected
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//기본키 생성을 데이터 베이스에 위임
    private Long id;

    @Column(nullable=false)//not null
    private String item;

    @Column(nullable=false)
    private Boolean isDone;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @ManyToOne
    @JsonBackReference
    //매핑할 외래키를 user entity의 id로 설정
    @JoinColumn(name = "user_id")
    private User user;

    // 생성자에 @Builder 적용
    @Builder
    public Todo(Long id, String item, Boolean isDone, User user){
        this.id=id;
        this.item=item;
        this.isDone=isDone;
        this.user=user;
    }
}