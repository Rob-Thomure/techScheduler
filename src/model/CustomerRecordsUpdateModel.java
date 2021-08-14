package model;

import database.DataSource;

public class CustomerRecordsUpdateModel {
    private int customerId;
    private int addressId;
    private String customerName;
    private String address;
    private String phone;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void updateCustomerDB(CustomerRecordsUpdateModel customerRecordsUpdateModel) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        dataSource.updateAddress(customerRecordsUpdateModel);
        dataSource.updateCustomer(customerRecordsUpdateModel);
    }
}
