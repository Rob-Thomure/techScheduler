package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class CustomerRecordsViewModel {
    private int customerId;
    private String customerName;
    private int addressId;
    private String address;
    private String phone;
    private int cityId;
    private int countryId;
    ArrayList<Customer> customers;

    public ObservableList<CustomerRecordsViewModel> getCustomers() {
        ObservableList<CustomerRecordsViewModel> customers = FXCollections.observableArrayList();
        setCustomers();
        for (Customer customer : this.customers) {
            CustomerRecordsViewModel customerRecordsViewModel = new CustomerRecordsViewModel();
            this.customerId = customer.getCustomerId();
            this.customerName = customer.getCustomerName();
            this.addressId = customer.getAddress().getAddressId();
            this.address = customer.getAddress().getAddress();
            this.phone = customer.getAddress().getPhone();
            this.cityId = customer.getAddress().getCityId();
            this.countryId = customer.getAddress().getCity().getCountryId();
            customers.add(customerRecordsViewModel);
        }
        return customers;
    }

    public void setCustomers() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        this.customers = dataSource.queryCustomer();
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

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public boolean checkIfHasAppointmentScheduled() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<Appointment> appointments = dataSource.queryAppointment();
        for (Appointment appointment : appointments) {
            if (this.customerId == appointment.getCustomerId()) {
                return true;
            }
        }
        return false;
    }

    public void deleteCustomerFromDB() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        dataSource.deleteFromTbl("customer", "customerId", this.customerId);
        dataSource.deleteFromTbl("address", "addressId", this.addressId);
        dataSource.deleteFromTbl("city", "cityId", this.cityId);
        dataSource.deleteFromTbl("country", "countryId", this.countryId);
    }

}
