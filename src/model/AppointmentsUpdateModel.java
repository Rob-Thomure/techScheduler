package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.util.ArrayList;

public class AppointmentsUpdateModel {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String type;
    private final String customerName;
    private final String userName;
    private final int customerId;
    private final int userId;
    private final int appointmentId;

    private static final ObservableList<String> hours = FXCollections.observableArrayList("01",
            "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
            "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    private static final ObservableList<String> minutes = FXCollections.observableArrayList("00",
            "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55");
    private static final ObservableList<String> meetingTypes = FXCollections.observableArrayList(
            "Planning", "Decision Making", "Progress Update");

    public AppointmentsUpdateModel(LocalDate date, String startHour, String startMinute, String endHour,
                                   String endMinute, String type, String customerName, String userName, int appointmentId) {
        this.start = convertToLocalDateTime(date, startHour, startMinute);
        this.end = convertToLocalDateTime(date, endHour, endMinute);
        this.type = type;
        this.customerName = customerName;
        this.userName = userName;
        this.customerId = getCustomerIdFromDB(customerName);
        this.userId = getUserIdFromDB(userName);
        this.appointmentId = appointmentId;
    }
    
    private int getCustomerIdFromDB(String customerName) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<Customer> customers = dataSource.queryCustomer();
        for (Customer customer: customers) {
            if (customerName.equals(customer.getCustomerName())) {
                return customer.getCustomerId();
            }
        }
        return -1;
    }

    private int getUserIdFromDB(String userName) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<User> users = dataSource.queryUser();
        for (User user: users) {
            if (userName.equals(user.getUserName())) {
                return user.getUserId();
            }
        }
        return -1;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getType() {
        return type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getUserName() {
        return userName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void updateAppointment(AppointmentsUpdateModel appointment) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        dataSource.updateAppointment(appointment);
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

    public static ObservableList<String> setCustomerComboBoxNames() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<Customer> customers = dataSource.queryCustomer();
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for (Customer customer: customers) {
            customerNames.add(customer.getCustomerName());
        }
        return customerNames;
    }

    public static ObservableList<String> setUserComboBoxNames() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<User> users = dataSource.queryUser();
        ObservableList<String> userNames = FXCollections.observableArrayList();
        for (User user: users) {
            userNames.add(user.getUserName());
        }
        return userNames;
    }

    private LocalDateTime convertToLocalDateTime(LocalDate date, String hour, String minute){
        return LocalDateTime.of(date.getYear(), date.getMonthValue(),
                date.getDayOfMonth(), Integer.parseInt(hour), Integer.parseInt(minute));
    }

    public boolean startIsAfterEnd() {
        return this.start.isAfter(this.end);
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

    public boolean notWithinBusinessHours() {
        LocalTime businessTimeStart = LocalTime.of(8, 0);
        LocalTime businessTimeEnd = LocalTime.of(17, 0);
        int dayOfWeek = this.start.getDayOfWeek().getValue();
        return this.start.toLocalTime().isBefore(businessTimeStart) || this.end.toLocalTime().isAfter(businessTimeEnd) ||
                dayOfWeek == 6 || dayOfWeek == 7;
    }

}
