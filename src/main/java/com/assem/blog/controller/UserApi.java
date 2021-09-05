package com.assem.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {

    @GetMapping("/api")
    public String getUser() {

        return "HelloWorld";
    }
}
