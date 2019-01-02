package com.booyue.springboot_demo.controller;


import com.booyue.springboot_demo.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @RequestMapping("/hello")
    public User hello() {
        return new User("tlh", 18);
    }
}
