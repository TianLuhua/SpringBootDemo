package com.booyue.springboot_demo.web;

import com.booyue.springboot_demo.model.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (loginUser != null) {
            //不拦截
            return true;
        } else {
            //拦截
            request.setAttribute("loginError", "请登录后访问！");
            request.getRequestDispatcher("/login.html").forward(request, response);
            return false;
        }
    }
}
