package com.booyue.springboot_demo.config;


import com.booyue.springboot_demo.domain.impl.ToYoTa;
import com.booyue.springboot_demo.domain.inter.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration 类似Spring中的IOC容器的配置文件,可以有多个@Configuration注解的IOC容器
 */
@Configuration
public class JavaConfigB {

    /**
     * @Bean()中写的话默认值就是方法名， @Bean("getToyota")
     * @return
     */
    @Bean("toyota")
    public Car getToyota() {
        return new ToYoTa();
    }




}
