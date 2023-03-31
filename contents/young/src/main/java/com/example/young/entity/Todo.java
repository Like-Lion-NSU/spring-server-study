package com.example.young.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import jakarta.persistence.*;


@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // 식별 id

    @Column(nullable=false)
    private String item;        // 일정

    @Column(nullable=false)
    private Boolean isDone;     // 완료 여부

    private LocalDateTime createdDate;      // 생성일
    
    private LocalDateTime updatedDate;      // 수정일

    @ManyToOne      // 다:1
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Todo(Long id, String item, Boolean isDone, User user){
        this.id=id;
        this.item=item;
        this.isDone=isDone;
        this.user=user;
    }
}
