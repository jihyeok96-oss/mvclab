package com.example.mvclab.member;

public class MemberNotFoundException extends Exception {
    public MemberNotFoundException(Long id) {
        System.out.println(id + "는 존재하지 않습니다.");
    }
}