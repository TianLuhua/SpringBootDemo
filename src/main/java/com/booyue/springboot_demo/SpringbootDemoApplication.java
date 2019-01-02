package com.booyue.springboot_demo;

import com.booyue.springboot_demo.domain.config.ConfigParsent;
import com.booyue.springboot_demo.domain.inter.Car;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class SpringbootDemoApplication {

    public static void main(String[] args) {
//		SpringApplication.run(SpringbootDemoApplication.class, args);
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ConfigParsent.class);
        Car toyota = (Car) configApplicationContext.getBean("toyota");
        toyota.print();
        Car bmw = (Car) configApplicationContext.getBean("bmw");
        bmw.print();
    }
}

