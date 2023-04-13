package com.springboot.todo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class Todo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String item;

    @Column(nullable=false)
    private Boolean isDone;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Todo(Long id,String item, Boolean isDone,User user){
        this.id=id;
        this.item=item;
        this.isDone=isDone;
        this.user=user;
    }

}
