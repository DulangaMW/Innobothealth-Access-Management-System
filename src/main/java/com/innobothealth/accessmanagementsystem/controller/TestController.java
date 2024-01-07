package com.innobothealth.accessmanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("say-hello")
    public String sayHello() {
        return "Hello InnobotHealth!!!";
    }

}
