package com.testdemo.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackendController {

    @GetMapping("/message")
    public String getHelloMsg() {
        return "Hello "+System.getenv("NAME");
    }
}

