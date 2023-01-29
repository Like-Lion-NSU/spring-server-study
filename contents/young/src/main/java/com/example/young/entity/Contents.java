package com.example.young.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Contents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;


    @Column(nullable = false)
    private String loginId;

    @Column
    private String doIt;

    @Column
    private boolean doCheck;
}
