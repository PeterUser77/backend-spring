package com.backendspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloWorld {
    
    @GetMapping
    public @ResponseBody String hello() {
        return "Bora Neto!!!!!!";
    }
}
