package com.springboot.todo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String items;

    @ManyToOne
    @JoinColumn(name = "title")
    private Todo todo;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    @Builder
    public Tasks(String items, User user){
        this.items= items;
        this.user = user;
    }

    public static Tasks createTasks(String items, User user, Todo todo){
        Tasks tasks = new Tasks();
        tasks.items = items;
        tasks.user = user;
        tasks.todo = todo;
        return tasks;
    }
}
