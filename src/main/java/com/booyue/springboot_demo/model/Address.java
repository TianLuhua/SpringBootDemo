package com.booyue.springboot_demo.model;

import javax.persistence.*;

@Entity
@Table(name = "t_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "address_info")
    private String addressInfo;


    public Address() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public Address(Integer id, String addressInfo) {
        this.id = id;
        this.addressInfo = addressInfo;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", addressInfo='" + addressInfo + '\'' +
                '}';
    }
}
