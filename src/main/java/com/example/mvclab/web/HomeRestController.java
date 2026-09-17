package com.example.mvclab.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {

    @GetMapping("/api/")
    public String api() {
        return "Hello";
    }
}