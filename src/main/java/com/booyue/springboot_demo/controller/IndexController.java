package com.booyue.springboot_demo.controller;


import com.booyue.springboot_demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @GetMapping("/index") //@RequestMapping(value = "/index" ,method = RequestMethod.GET)
    public String getIndex(Model model) {
        List<User> users = new ArrayList<>();
        User user0 = new User(10, "booyue", "12ds3456dsa");
        User user1 = new User(12, "tianlu", "a12345dasd6");
        User user2 = new User(30, "tianhua", "ad123ad456");
        users.add(user0);
        users.add(user1);
        users.add(user2);
        model.addAttribute("users", users);
        return "users";
    }

}
