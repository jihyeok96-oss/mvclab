package com.example.mvclab.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Member {
    private Long id;
    private String name;
    private String password;
    private String email;
    private Integer age;
    private LocalDateTime createdAt;
}