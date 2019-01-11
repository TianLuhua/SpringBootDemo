package com.booyue.springboot_demo;

import com.booyue.springboot_demo.config.ConfigParsent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan({"com.booyue.springboot_demo.dao"})
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
//        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ConfigParsent.class);
//        Car toyota = (Car) configApplicationContext.getBean("toyota");
//        toyota.print();
//        Car bmw = (Car) configApplicationContext.getBean("bmw");
//        bmw.print();
    }
}

