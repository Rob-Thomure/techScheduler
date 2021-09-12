package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class CustomerRecordsViewModel {
    private final String customerName;
    private final int customerId;
    private final String address;
    private final int addressId;
    private final String phone;

    public CustomerRecordsViewModel(String customerName, int customerId, String address, int addressId, String phone) {
        this.customerName = customerName;
        this.customerId = customerId;
        this.address = address;
        this.addressId = addressId;
        this.phone = phone;
    }

    public  static ObservableList<CustomerRecordsViewModel> buildCustomerList() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<Customer> customers = dataSource.queryCustomer();
        ObservableList<CustomerRecordsViewModel> customersObservableList = FXCollections.observableArrayList();
        for (Customer customer : customers) {
            CustomerRecordsViewModel customerRecordsViewModel = new CustomerRecordsViewModel(customer.getCustomerName(),
                    customer.getCustomerId(), customer.getAddress().getAddress(), customer.getAddress().getAddressId(),
                    customer.getAddress().getPhone());
            customersObservableList.add(customerRecordsViewModel);
        }
        return customersObservableList;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getPhone() {
        return phone;
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
}
