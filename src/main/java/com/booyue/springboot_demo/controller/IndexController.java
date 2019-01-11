package com.booyue.springboot_demo.controller;


import com.booyue.springboot_demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/index") //@RequestMapping(value = "/index" ,method = RequestMethod.GET)
    public String getIndex(Model model) {
        List<User> users = new ArrayList<>();
//        User user0 = new User(10, "booyue", "12ds3456dsa");
//        User user1 = new User(12, "tianlu", "a12345dasd6");
//        User user2 = new User(30, "tianhua", "ad123ad456");
//        users.add(user0);
//        users.add(user1);
//        users.add(user2);
        model.addAttribute("users", users);
        return "users";
    }


    @GetMapping("/index.html")
    public String index(Model model) {
        String sql = "select * from customers";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        model.addAttribute("maps", list);
        return "index";
    }
}
