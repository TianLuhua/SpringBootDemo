package com.booyue.springboot_demo.model;

import javax.persistence.*;
import java.util.Date;

//通过@Entity 表明是一个映射的实体类， @Id表明id， @GeneratedValue 字段自动生成
@Entity
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String sellerName;
    private String sellerAddress;
    private String sellerContent;
    private String sellerType;
    private Integer sales;
    private Date sellerDate;

    public Seller() {
    }

    public Seller(Integer id, String sellerName, String sellerAddress, String sellerContent, String sellerType, Integer sales, Date sellerDate) {
        this.id = id;
        this.sellerName = sellerName;
        this.sellerAddress = sellerAddress;
        this.sellerContent = sellerContent;
        this.sellerType = sellerType;
        this.sales = sales;
        this.sellerDate = sellerDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getSellerContent() {
        return sellerContent;
    }

    public void setSellerContent(String sellerContent) {
        this.sellerContent = sellerContent;
    }

    public String getSellerType() {
        return sellerType;
    }

    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Date getSellerDate() {
        return sellerDate;
    }

    public void setSellerDate(Date sellerDate) {
        this.sellerDate = sellerDate;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", sellerName='" + sellerName + '\'' +
                ", sellerAddress='" + sellerAddress + '\'' +
                ", sellerContent='" + sellerContent + '\'' +
                ", sellerType='" + sellerType + '\'' +
                ", sales='" + sales + '\'' +
                ", sellerDate=" + sellerDate +
                '}';
    }
}
