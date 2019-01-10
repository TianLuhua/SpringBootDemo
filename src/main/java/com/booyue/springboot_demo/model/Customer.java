package com.booyue.springboot_demo.model;

public class Customer {
    private Integer id;
    private Integer custId;
    private String custName;
    private String custCity;
    private String custAddress;
    private String custContact;
    private String custSex;


    public Customer() {

    }

    public Customer(Integer id, Integer custId, String custName, String custCity, String custAddress, String custContact, String custSex) {
        this.id = id;
        this.custId = custId;
        this.custName = custName;
        this.custCity = custCity;
        this.custAddress = custAddress;
        this.custContact = custContact;
        this.custSex = custSex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustContact() {
        return custContact;
    }

    public void setCustContact(String custContact) {
        this.custContact = custContact;
    }

    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custCity='" + custCity + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custContact='" + custContact + '\'' +
                ", custSex='" + custSex + '\'' +
                '}';
    }
}
