package com.booyue.springboot_demo.dao;

import com.booyue.springboot_demo.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SellerDao extends JpaRepository<Seller, Integer> {
}
