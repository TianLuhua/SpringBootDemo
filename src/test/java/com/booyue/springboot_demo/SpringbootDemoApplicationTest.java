package com.booyue.springboot_demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void connectTest() throws SQLException {

        System.out.println(dataSource.getClass());

//        String sql = "select * from customers";
//        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//        System.out.println(list);

    }


}
