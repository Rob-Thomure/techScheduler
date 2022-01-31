package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class CustomerRecords {
    private int customerId;
    private String customerName;

    private int addressId;
    private String address;
    private String phone;

    private int cityId;
    private String city;

    private int countryId;
    private String country;

    public CustomerRecords(int customerId, String customerName, int addressId, String address, String phone, int citId,
                           String city, int countryId, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.address = address;
        this.phone = phone;
        this.cityId = citId;
        this.city = city;
        this.countryId = countryId;
        this.country = country;
    }

    public CustomerRecords(String customerName, String address, String city, String country, String phone) {
        this.customerName = customerName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phone = phone;
    }

    public CustomerRecords(int customerId, int addressId, String customerName, String address, String phone) {
        this.customerId = customerId;
        this.addressId = addressId;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
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

    public int getCityId() {
        return cityId;
    }

    public String getCity() {
        return city;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountry() {
        return country;
    }

    public static ObservableList<CustomerRecords> buildCustomerList() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<Customer> customers = dataSource.queryCustomer();
        ObservableList<CustomerRecords> customersObservableList = FXCollections.observableArrayList();
        for (Customer customer : customers) {
            CustomerRecords customerRecords = new CustomerRecords(customer.getCustomerId(), customer.getCustomerName(),
                    customer.getAddress().getAddressId(), customer.getAddress().getAddress(),
                    customer.getAddress().getPhone(), customer.getAddress().getCity().getCityId(),
                    customer.getAddress().getCity().getCity(),
                    customer.getAddress().getCity().getCountry().getCountryId(),
                    customer.getAddress().getCity().getCountry().getCountry());
            customersObservableList.add(customerRecords);
        }
        return customersObservableList;
    }

    public boolean hasAppointmentScheduled(String customerName) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        int customerId = dataSource.customerIdQuery(customerName);
        ArrayList<Appointment> appointments = dataSource.queryAppointment();
        for (Appointment appointment: appointments) {
            if (customerId == appointment.getCustomer().getCustomerId()) {
                return true;
            }
        }
        return false;
    }

    public void deleteCustomerFromDB(String customerName) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        int customerId = dataSource.customerIdQuery(customerName);
        int addressId = dataSource.addressIdQuery(customerId);
        int cityId = dataSource.cityIdQuery(addressId);
        int countryId = dataSource.countryIdQuery(cityId);
        dataSource.deleteFromTbl("customer", "customerId", customerId);
        dataSource.deleteFromTbl("address", "addressId", addressId);
        dataSource.deleteFromTbl("city", "cityId", cityId);
        dataSource.deleteFromTbl("country", "countryId", countryId);
    }

    public void addCustomerToDB(CustomerRecords customerRecords) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        this.countryId = dataSource.insertCountry(customerRecords);
        this.cityId = dataSource.insertCity(customerRecords, countryId);
        this.addressId = dataSource.insertAddress(customerRecords, cityId);
        dataSource.insertCustomer(customerRecords, addressId);
    }

    public void updateCustomerDB(CustomerRecords customerRecords) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        dataSource.updateAddress(customerRecords);
        dataSource.updateCustomer(customerRecords);
    }


}
