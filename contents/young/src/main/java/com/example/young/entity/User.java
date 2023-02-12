package com.example.young.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    private List<Todo> todoList = new ArrayList<>();

    @Builder
    public User(String userId, String name, String password, List<Todo> todoList) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.todoList = todoList;
    }
}
