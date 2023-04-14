package com.example.young.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import jakarta.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // 식별 id

    @Column(nullable=false)
    @NotNull
    @Size(min = 1, max = 100, message = "일정 글자 수는 1~100자리 입니다.")
    private String item;        // 일정

    @Column(nullable=false)
    @NotNull
//    @PositiveOrZero
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
