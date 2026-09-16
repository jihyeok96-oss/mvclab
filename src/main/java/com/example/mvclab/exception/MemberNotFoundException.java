package com.example.mvclab.exception;

public class MemberNotFoundException extends Exception {
    public MemberNotFoundException(Long id) {
        System.out.println(id + " 존재하지 않습니다.");
    }
}