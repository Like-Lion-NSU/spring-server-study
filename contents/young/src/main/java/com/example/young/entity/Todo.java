package com.example.young.entity;

import com.example.young.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String item;

    @Column
    private Boolean isDone;

    private LocalDateTime createdDate;
    
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Todo(Long id, String item, Boolean isDone, LocalDateTime createdDate, LocalDateTime updatedDate){
        this.id=id;
        this.item=item;
        this.isDone=isDone;
        this.createdDate=createdDate;
        this.updatedDate=updatedDate;
    }
}
