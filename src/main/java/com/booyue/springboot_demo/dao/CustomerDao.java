package com.booyue.springboot_demo.dao;

import com.booyue.springboot_demo.model.Customer;

public interface CustomerDao {
    // 增
    public void addCustomer(Customer customer);

    // 删
    public void deleteCustomer(Integer id);

    //改
    public void updateCustomer(Customer customer);

    //查
    public Customer getCustomer(Integer id);
}
