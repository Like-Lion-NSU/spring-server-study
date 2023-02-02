package com.example.young.entity;

import jakarta.persistence.*;
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
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String passWord;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "User")
    private List<Todo> todoList = new ArrayList<>();

}
