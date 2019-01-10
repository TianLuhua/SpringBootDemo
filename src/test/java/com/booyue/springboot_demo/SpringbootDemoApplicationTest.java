package com.booyue.springboot_demo;


import com.booyue.springboot_demo.dao.CustomerDao;
import com.booyue.springboot_demo.dao.SellerDao;
import com.booyue.springboot_demo.model.Seller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTest {

//    @Autowired
//    private CustomerDao customerDao;

    @Autowired
    private SellerDao sellerDao;

    @Test
    public void connectTest() throws SQLException {

//        System.out.println(customerDao.getClass());


        //add
//        Customer customer = new Customer();
//        customer.setCustId(123);
//        customer.setCustName("huahua");
//        customer.setCustCity("湖南.湘西");
//        customer.setCustAddress("湖南.湘西.保靖");
//        customer.setCustContact("湖南.湘西.保靖.清水坪");
//        customer.setCustSex("M");
//        customerDao.addCustomer(customer);

        //delate
//         customerDao.deleteCustomer(978);

        //update
//        Customer customer=new Customer();
//        customer.setId(977);
//        customer.setCustId(110);
//        customer.setCustName("田露华");
//        customer.setCustCity("湖南.湘西");
//        customer.setCustAddress("湖南.湘西.保靖");
//        customer.setCustContact("湖南.湘西.保靖.清水坪");
//        customer.setCustSex("M");
//        customerDao.updateCustomer(customer);

        //select
//        Customer customer = customerDao.getCustomer(977);
//        System.out.println(customer.toString());

    }

    @Test
    public void testJpa() {
        System.out.println(sellerDao.getClass());
        Seller seller = new Seller();
        seller.setSellerName("快乐水");
        seller.setSellerAddress("超市");
        seller.setSellerContent("具体什么内容，我也不知道");
        seller.setSellerType("饮料");
        seller.setSales(1);
        seller.setSellerDate(new Date());
        sellerDao.save(seller);
    }


}
