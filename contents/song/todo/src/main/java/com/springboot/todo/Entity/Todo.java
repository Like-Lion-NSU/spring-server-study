package com.springboot.todo.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
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
    @JoinColumn(name="user_id")
    private User user;

}
