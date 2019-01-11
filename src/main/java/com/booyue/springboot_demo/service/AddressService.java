package com.booyue.springboot_demo.service;

import com.booyue.springboot_demo.model.Address;

import java.util.List;

public interface AddressService {

    //增
    void add(Address address);

    //删
    void delete(Integer addressId);

    //改
    void update(Address address);

    //查
    Address get(Integer addressId);

    //获取所有地址信息
    List<Address> getAllAddresses();

}
