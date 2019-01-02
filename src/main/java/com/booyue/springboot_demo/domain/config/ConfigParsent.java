package com.booyue.springboot_demo.domain.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * 可以使用一个总的配置文件来将其他的配置文件综合成一个配置文件
 * 1.用@Configuration注释
 * 2.用@Import将其他的配置文件导入
 */
@Configuration
@Import({JavaConfigA.class, JavaConfigB.class})
public class ConfigParsent {
}
