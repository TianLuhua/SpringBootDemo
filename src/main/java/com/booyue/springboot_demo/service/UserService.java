package com.booyue.springboot_demo.service;

import com.booyue.springboot_demo.model.User;

import java.util.List;

public interface UserService {

    //增
    void add(User user);

    //删
    void delete(Integer userId);

    //改
    void update(User user);

    //查
    User get(Integer userId);

    //获取所有用户
    List<User> getAllUsers();

    //根据用户名获取用户

    //判断登录:1.通过用户名查询数据库，是否存在对应的用户记录。
    User login(User user);


    //根据名字模糊查询用户信息
    List<User> getUserByLikeName(String likeName);

}
