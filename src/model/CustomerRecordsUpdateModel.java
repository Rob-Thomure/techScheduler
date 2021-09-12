package model;

import database.DataSource;

public class CustomerRecordsUpdateModel {
    private final int customerId;
    private final int addressId;
    private final String customerName;
    private final String address;
    private final String phone;

    public CustomerRecordsUpdateModel(int customerId, int addressId, String customerName, String address, String phone) {
        this.customerId = customerId;
        this.addressId = addressId;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void updateCustomerDB(CustomerRecordsUpdateModel customerRecordsUpdateModel) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        dataSource.updateAddress(customerRecordsUpdateModel);
        dataSource.updateCustomer(customerRecordsUpdateModel);
    }
}
