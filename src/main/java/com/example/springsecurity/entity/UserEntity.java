package com.example.springsecurity.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true) // 중복방지
    private String username;
    private String password;

    private String role;


}
