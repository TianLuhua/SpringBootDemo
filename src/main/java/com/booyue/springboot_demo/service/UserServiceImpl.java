package com.booyue.springboot_demo.service;

import com.booyue.springboot_demo.dao.UserDao;
import com.booyue.springboot_demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void add(User user) {
        userDao.save(user);
    }

    @Override
    public void delete(Integer userId) {
        userDao.deleteById(userId);

    }

    @Override
    public void update(User user) {
        userDao.saveAndFlush(user);
    }

    @Override
    public User get(Integer userId) {
        return userDao.findById(userId).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User login(User user) {
        /**
         * 登录成功返回一个User对象，登录失败返回null
         */
        //1.根据用户名称到数据库查询是否存在对应的用户记录
        User u = userDao.getUserByName(user.getName());
        if (u == null) {
            return null;
        }
        //2. 判断密码对不对
        if (!(u.getPassword().equals(user.getPassword()))) {
            return null;
        } else {
            return u;
        }
    }

    @Override
    public List<User> getUserByLikeName(String likeName) {
        return userDao.getByNameIsLike("%"+likeName+"%");
    }
}
