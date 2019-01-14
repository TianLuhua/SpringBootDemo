package com.booyue.springboot_demo;

import com.booyue.springboot_demo.config.ConfigParsent;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@MapperScan({"com.booyue.springboot_demo.dao"})
@EnableTransactionManagement
@Log4j
public class SpringbootDemoApplication {


    @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager) {
        log.info("===================:" + platformTransactionManager.getClass().getName());
        return new Object();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
//        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ConfigParsent.class);
//        Car toyota = (Car) configApplicationContext.getBean("toyota");
//        toyota.print();
//        Car bmw = (Car) configApplicationContext.getBean("bmw");
//        bmw.print();
    }
}

