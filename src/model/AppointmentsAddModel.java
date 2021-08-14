package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AppointmentsAddModel {
    private int customerId;
    private String customerName;
    private int userId;
    private String userName;
    private int appointmentId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String type;
    private ObservableList<String> customers = FXCollections.observableArrayList();
    private ObservableList<String> users = FXCollections.observableArrayList();
    private ObservableList<AppointmentsAddModel> appointments = FXCollections.observableArrayList();


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ObservableList<String> getCustomers() {
        return this.customers;
    }

    public void setCustomers() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        this.customers = dataSource.customerQuery();
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public ObservableList<String> getUsers() {
        return users;
    }

    public void setUsers() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        this.users = dataSource.userQuery();
    }

    public int getCustomerIdFromDB(String customerName) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        return dataSource.customerIdQuery(customerName);
    }

    public int getUserIdFromDB(String userName) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        return dataSource.userIdQuery(userName);
    }

    public ObservableList<AppointmentsAddModel> getAppointments() {
        return appointments;
    }

    public void setAppointments() {
        //TODO implement this
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        this.appointments = dataSource.conflictingAppointmentsQuery();
    }

    public boolean checkForConflictingAppointments() {
        for (AppointmentsAddModel appointment : appointments) {
            //TODO implement this method
            if(this.customerId == appointment.getCustomerId() || this.userId == appointment.getUserId()) {
                if ((this.start.isAfter(appointment.getStart()) && this.start.isBefore(appointment.getEnd()))
                        || (this.end.isAfter(appointment.getStart()) && this.end.isBefore(appointment.getEnd()))
                        || (this.start.isEqual(appointment.getStart()))
                        || (this.end.isEqual(appointment.getEnd()))
                        || (appointment.getStart().isAfter(this.start) && appointment.getStart().isBefore(this.end))
                        || (appointment.getEnd().isAfter(this.start) && appointment.getEnd().isBefore(this.end)) ) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addAppointmentToDB(AppointmentsAddModel appointmentsAddModel) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        dataSource.insertAppointment(appointmentsAddModel);
    }

    public LocalDateTime convertToUTC(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId).withZoneSameInstant(zoneId.of("UTC"));
        return zonedDateTime.toLocalDateTime();
    }

}
