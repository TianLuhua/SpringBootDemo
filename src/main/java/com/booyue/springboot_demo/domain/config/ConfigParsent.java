package com.booyue.springboot_demo.domain.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.ResourceServlet;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 可以使用一个总的配置文件来将其他的配置文件综合成一个配置文件
 * 1.用@Configuration注释
 * 2.用@Import将其他的配置文件导入
 * <p>
 * 需要扩展springMVC的配置，需要实现WebMvcConfigurer接口
 */
@Configuration //表示该类为配置类
@Import({JavaConfigA.class, JavaConfigB.class})
public class ConfigParsent implements WebMvcConfigurer {

    @ConfigurationProperties(prefix = "spring.datasource")//从配置文件（application.yaml）中读取属性，注入到Datasource中
    @Bean //表示将DruidDataSource的实例注入到Spring的ioc容器中去
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    //配置对整个应用监控：
    @Bean
    public ServletRegistrationBean getViewServelt() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet());
        String[] urlArr = new String[]{"/druid/*"};
        bean.setUrlMappings(Arrays.asList(urlArr));
        Map<String, String> map = new HashMap<>();
        //这里可以设置Druid后台登录用户名和密码
        map.put(ResourceServlet.PARAM_NAME_USERNAME, "tianluhua");
        map.put(ResourceServlet.PARAM_NAME_PASSWORD, "123456");
        bean.setInitParameters(map);
        return bean;
    }

    //配置过滤器，来实现对整个web的请求进行过滤，从而实现对web应用对数据库的访问
    @Bean
    public FilterRegistrationBean getFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        bean.setUrlPatterns(Arrays.asList(new String[]{"/*"}));
        //不能对所有的请求拦截，比如：静态资源，druid的后台请求
        Map<String, String> map = new HashMap<>();
        map.put(WebStatFilter.PARAM_NAME_EXCLUSIONS, "*.js,*.css,*.png,/druid/*");
        bean.setInitParameters(map);
        return bean;
    }

}
