package com.booyue.springboot_demo.controller;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = {java.lang.NullPointerException.class})
    public ModelAndView exception(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", e);//注入错误类，可以在自定义的视图中显示信息
        modelAndView.setViewName("myerror");//指定错误视图名
        return modelAndView;
    }
}
