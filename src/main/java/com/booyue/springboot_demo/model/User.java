package com.booyue.springboot_demo.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "t_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;
    @Column(name = "reg_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//解决用户修改日期后，String再次封装成Date时报错问题
    private Date regDate;
    @ManyToOne//一对多
    @JoinColumn(name = "address_id")//外键
    private Address address;

    public User() {
    }

    public User(Integer id, String name, String password, Date regDate, Address address) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.regDate = regDate;
        this.address = address;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", regDate=" + regDate +
                ", address=" + address +
                '}';
    }
}
