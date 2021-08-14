/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author robertthomure
 */
public class Customer {


    private String customerName;
    private int customerId;
    private String streetAddress;
    private int addressId;
    private String phone;

    private Address address;

    public Customer(int customerId, String customerName, Address address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
    }

    public Customer(String customerName, int customerId, String streetAddress, int addressId, String phone) {
        this.customerName = customerName;
        this.customerId = customerId;
        this.streetAddress = streetAddress;
        this.addressId = addressId;
        this.phone = phone;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }
}