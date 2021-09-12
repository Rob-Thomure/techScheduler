package model;

import database.DataSource;

public class CustomerRecordsAddModel {
    private final String CustomerName;
    private final String address;
    private final String city;
    private final String country;
    private final String phone;

    public CustomerRecordsAddModel(String customerName, String address, String city, String country, String phone) {
        CustomerName = customerName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phone = phone;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
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
