package com.booyue.springboot_demo.controller;


import com.booyue.springboot_demo.dao.AddressDao;
import com.booyue.springboot_demo.model.Address;
import com.booyue.springboot_demo.model.User;
import com.booyue.springboot_demo.service.AddressService;
import com.booyue.springboot_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private AddressService addressService;

    @GetMapping("/login.html")
    public String loginPage(HttpSession session) {
        User loginUSer = (User) session.getAttribute("loginUser");
        if (loginUSer == null) {
            return "login";
        } else {
            return "redirect:/main.html";
        }
    }

    @PostMapping("/login.html")
    public String login(User user, HttpSession session, Model model) {
        //接收页面的userName、password，调用服务层的login方法验证。成功：将返回的user保存到seesion域空间；失败：返回失败信息

        User u = userService.login(user);
        if (u != null) {
            //成功：页面跳转 -->main页面.
            //把 u 存放到session的域空间：
            session.setAttribute("loginUser", u);
            //跳转：
            return "redirect:/main.html";
        } else {
            //失败：
            model.addAttribute("loginError", "用户名或者密码错误！");
            return "login";
        }
    }

    @GetMapping("/main.html")
    public String main() {
        return "main";
    }


    @GetMapping("/logout.html")
    public String logOut(HttpSession session) {
        //清空seerion里面的内容
        session.invalidate();
        return "redirect:/logout.html";
    }

    @GetMapping("/userlist.html")
    public String userList(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userlist";
    }

    @GetMapping("/deleteuser.html")
    public String deleteUser(Integer id) {
        userService.delete(id);
        return "redirect:/userlist.html";
    }

    @GetMapping("/updateuser.html")
    public String updateUser(Integer id, Model model) {
        List<Address> addresses = addressService.getAllAddresses();
        User user = userService.get(id);
        model.addAttribute("addresses", addresses);
        model.addAttribute("user", user);
        return "updateuser";
    }

    @PostMapping("/updateuser.html")
    public String updateUser(User user) {
        userService.update(user);
        return "redirect:/userlist.html";
    }


    @PostMapping("/userSearch.html")
    public String userSearch(String searchByuserName, Model model) {
        List<User> users = userService.getUserByLikeName(searchByuserName);
        model.addAttribute("users", users);
        return "userlist";
    }


    @GetMapping("/adduser.html")
    public String addUser(Model model) {
        List<Address> addresses = addressService.getAllAddresses();
        model.addAttribute("addresses", addresses);
        return "adduser";
    }


    @PostMapping("/adduser.html")//在提交到该方法时，form表单的name名称必须和user的属相名字一致
    public String addUser(User user) {
        user.setRegDate(new Date());
        userService.add(user);
        return "redirect:/userlist.html";
    }

}
