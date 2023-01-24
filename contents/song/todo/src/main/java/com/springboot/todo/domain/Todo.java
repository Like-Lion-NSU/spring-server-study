package com.springboot.todo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    @Builder
    public Todo(String title, User user){
        this.title = title;
        this.user = user;
    }

    public static Todo createTodo(String title, User user){
        Todo todo = new Todo();
        todo.title = title;
        todo.user = user;
        return todo;

    }
}
