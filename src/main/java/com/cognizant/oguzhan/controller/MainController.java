package com.cognizant.oguzhan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/OguzhanApp")
    public String hello() {
        return "Hello, all API tests were successful";
    }
}
