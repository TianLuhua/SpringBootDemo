package com.booyue.springboot_demo.dao;

import com.booyue.springboot_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    User getUserByName(String userName);

    List<User> getByNameIsLike(String likeName);
}
