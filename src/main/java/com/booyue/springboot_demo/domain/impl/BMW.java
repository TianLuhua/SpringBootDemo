package com.booyue.springboot_demo.domain.impl;

import com.booyue.springboot_demo.domain.inter.Car;
import org.springframework.stereotype.Component;

@Component
public class BMW implements Car {
    @Override
    public void print() {
        System.out.println("I am BMW !");
    }
}
