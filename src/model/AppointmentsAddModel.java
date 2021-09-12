package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsAddModel {

    private final String type;
    private String customerName;
    private final String userName;

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final int customerId;
    private final int userId;

    public AppointmentsAddModel(LocalDate date, String startHour, String startMinute, String endHour, String endMinute,
                                String type, String customerName, String userName) {
        this.type = type;
        this.customerName = customerName;
        this.userName = userName;
        this.start = convertToLocalDateTime(date, startHour, startMinute);
        this.end = convertToLocalDateTime(date, endHour, endMinute);
        this.customerId = getCustomerIdFromDB(customerName);
        this.userId = getUserIdFromDB(userName);
    }

    private static final ObservableList<String> hours = FXCollections.observableArrayList("01",
            "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
            "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    private static final ObservableList<String> minutes = FXCollections.observableArrayList("00",
            "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55");
    private static final ObservableList<String> meetingTypes = FXCollections.observableArrayList(
            "Planning", "Decision Making", "Progress Update");

    public int getCustomerId() {
        return customerId;
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

    public String getUserName() {
        return userName;
    }

    public String getType() {
        return type;
    }

    public static ObservableList<String> getCustomers() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        List<Customer> customers = dataSource.queryCustomer();
        return buildCustomerNameList(customers);
    }

    private static ObservableList<String> buildCustomerNameList(List<Customer> customers) {
        ObservableList<String> customerNameList = FXCollections.observableArrayList();
        for (Customer customer: customers) {
            customerNameList.add(customer.getCustomerName());
        }
        return customerNameList;
    }


    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public static ObservableList<String> getUsers() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        List<User> users = dataSource.queryUser();
        return buildUserNameList(users);
    }

    private static ObservableList<String> buildUserNameList(List<User> users) {
        ObservableList<String> userNameList = FXCollections.observableArrayList();
        for (User user: users) {
            userNameList.add(user.getUserName());
        }
        return userNameList;
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

    public boolean conflictsWithExistingAppointment() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<Appointment> appointments = dataSource.queryAppointment();
        for (Appointment appointment : appointments) {
            if(this.customerId == appointment.getCustomer().getCustomerId() ||
                    this.userId == appointment.getUser().getUserId()) {
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

    public static ObservableList<String> getHours() {
        return hours;
    }

    public static ObservableList<String> getMinutes() {
        return minutes;
    }

    public static ObservableList<String> getMeetingTypes() {
        return meetingTypes;
    }

    public boolean startIsAfterEnd() {
        return start.isAfter(end);
    }

    public boolean notWithinBusinessHours() {
        LocalTime businessTimeStart = LocalTime.of(8, 0);
        LocalTime businessTimeEnd = LocalTime.of(17, 0);
        int dayOfWeek = this.start.getDayOfWeek().getValue();
        return this.start.toLocalTime().isBefore(businessTimeStart) || this.end.toLocalTime().isAfter(businessTimeEnd) ||
                dayOfWeek == 6 || dayOfWeek == 7;
    }

    private LocalDateTime convertToLocalDateTime(LocalDate date, String hour, String minute) {
        return LocalDateTime.of(date.getYear(), date.getMonthValue(),
                date.getDayOfMonth(), Integer.parseInt(hour), Integer.parseInt(minute));
    }
}