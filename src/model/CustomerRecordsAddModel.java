package model;

import database.DataSource;

public class CustomerRecordsAddModel {
    private String CustomerName;
    private String address;
    private String city;
    private String country;
    private String phone;

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        this.CustomerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void addCustomerToDB(CustomerRecordsAddModel customerRecordsAddModel) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        int countryId = dataSource.insertCountry(customerRecordsAddModel);
        int cityId = dataSource.insertCity(customerRecordsAddModel, countryId);
        int addressId = dataSource.insertAddress(customerRecordsAddModel, cityId);
        dataSource.insertCustomer(customerRecordsAddModel, addressId);
    }
}
