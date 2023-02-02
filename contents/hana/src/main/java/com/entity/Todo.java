package com.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private String item;
    @Column
    private Boolean isDone; //완료 여부

    private LocalDateTime createdDate; //생성날짜

    private LocalDateTime updatedDate; //마감날짜
}
