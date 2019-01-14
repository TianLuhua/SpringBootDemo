package com.booyue.springboot_demo.service;

import com.booyue.springboot_demo.dao.AddressDao;
import com.booyue.springboot_demo.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;


    @Override
    public void add(Address address) {
        addressDao.save(address);
    }

    @Override
    public void delete(Integer addressId) {
        addressDao.deleteById(addressId);
    }

    @Override
    public void update(Address address) {
        addressDao.saveAndFlush(address);
    }

    @Override
    public Address get(Integer addressId) {
        return addressDao.findById(addressId).get();
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressDao.findAll();
    }
}
