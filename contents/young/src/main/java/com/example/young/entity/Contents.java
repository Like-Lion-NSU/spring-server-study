package com.example.young.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Data
@NoArgsConstructor
public class Contents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @ManyToOne
    @JoinColumn(name = "User")
    @Column(nullable = false)
    private User loginId;

    @Column
    private String list;

    @Column
    private boolean check;
}
