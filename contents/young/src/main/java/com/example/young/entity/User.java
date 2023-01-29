package com.example.young.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String loginPassWord;

    @Column(nullable = false)
    private String nickName;
}
