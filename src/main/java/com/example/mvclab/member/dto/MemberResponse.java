package com.example.mvclab.member.dto;

import com.example.mvclab.member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({
        "id",
        "name",
        "email",
        "age",
        "createdAt"
})
public class MemberResponse {
    private final Long id;

    private final String name;

    private final String email;

    private final Integer age;

    @JsonIgnore
    private final String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.age = member.getAge();
        this.password = member.getPassword();
        this.createdAt = member.getCreatedAt();
    }
}