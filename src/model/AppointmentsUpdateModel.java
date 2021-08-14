package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.util.ArrayList;

public class AppointmentsUpdateModel {
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private String type;
    private String customerName;
    private String userName;
    private int appointmentId;
    private ArrayList<Appointment> appointments;
    private ArrayList<Customer> customers;
    private ArrayList<User> users;
    private ObservableList<String> customersComboBox;
    private ObservableList<String> usersComboBox;

    private int customerId;
    private int userId;
    private LocalDateTime localDateTimeStart;
    private LocalDateTime localDateTimeEnd;

    public LocalDateTime getLocalDateTimeStart() {
        return localDateTimeStart;
    }

    public LocalDateTime getLocalDateTimeEnd() {
        return localDateTimeEnd;
    }

    public void setLocalDateTimeStart(LocalDateTime localDateTimeStart) {
        this.localDateTimeStart = localDateTimeStart;
    }

    public void setLocalDateTimeEnd(LocalDateTime localDateTimeEnd) {
        this.localDateTimeEnd = localDateTimeEnd;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerIdFromCustomers(String customerName) {
        for (Customer customer : customers) {
            if (customerName.equals(customer.getCustomerName())) {
                return customer.getCustomerId();
            }
        }
        return -1;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserIdFromCustomers(String userName) {
        for (User user : users) {
            if (userName.equals(user.getUserName())) {
                return user.getUserId();
            }
        }
        return -1;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setAppointments() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        this.appointments = dataSource.queryAppointment();
    }

    public void setCustomers() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        this.customers = dataSource.queryCustomer();
    }

    public void setUsers() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        this.users = dataSource.queryUser();
    }

    public ObservableList<String> getCustomersComboBox() {
        return customersComboBox;
    }

    public void setCustomersComboBox() {
        ObservableList<String> customersComboBox = FXCollections.observableArrayList();
        for (Customer customer : customers) {
            customersComboBox.add(customer.getCustomerName());
        }
        this.customersComboBox = customersComboBox;
    }

    public ObservableList<String> getUsersComboBox() {
        return usersComboBox;
    }

    public void setUsersComboBox() {
        ObservableList<String> usersComboBox = FXCollections.observableArrayList();
        for (User user : users) {
            usersComboBox.add(user.getUserName());
        }
        this.usersComboBox = usersComboBox;
    }

    public boolean checkForConflictingAppointments() {
        for (Appointment appointment : appointments) {
            if(this.customerId == appointment.getCustomerId() || this.userId == appointment.getUserId()) {
                if ((this.localDateTimeStart.isAfter(appointment.getStart()) && this.localDateTimeStart.isBefore(appointment.getEnd()))
                        || (this.localDateTimeEnd.isAfter(appointment.getStart()) && this.localDateTimeEnd.isBefore(appointment.getEnd()))
                        || (this.localDateTimeStart.isEqual(appointment.getStart()))
                        || (this.localDateTimeEnd.isEqual(appointment.getEnd()))
                        || (appointment.getStart().isAfter(this.localDateTimeStart) && appointment.getStart().isBefore(this.localDateTimeEnd))
                        || (appointment.getEnd().isAfter(this.localDateTimeStart) && appointment.getEnd().isBefore(this.localDateTimeEnd)) ) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateAppointment(AppointmentsUpdateModel appointment) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        dataSource.updateAppointment(appointment);
    }

    public LocalDateTime convertToUTC(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId).withZoneSameInstant(zoneId.of("UTC"));
        return zonedDateTime.toLocalDateTime();
    }



}
