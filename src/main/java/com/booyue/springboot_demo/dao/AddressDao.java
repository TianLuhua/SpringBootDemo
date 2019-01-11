package com.booyue.springboot_demo.dao;

import com.booyue.springboot_demo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<Address, Integer> {
}
