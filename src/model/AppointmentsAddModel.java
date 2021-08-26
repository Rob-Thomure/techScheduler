package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.util.ArrayList;

public class AppointmentsAddModel {

    private LocalDate date;
    private String startHour;
    private String startMinute;
    private String endHour;
    private String endMinute;
    private String type;
    private String customerName;
    private String userName;

    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;

    public AppointmentsAddModel(LocalDate date, String startHour, String startMinute, String endHour, String endMinute,
                                String type, String customerName, String userName) {
        this.date = date;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.type = type;
        this.customerName = customerName;
        this.userName = userName;

        this.start = convertToLocalDateTime(startHour, startMinute);
        this.end = convertToLocalDateTime(endHour, endMinute);
        this.customerId = getCustomerIdFromDB(customerName);
        this.userId = getUserIdFromDB(userName);
    }





    private int appointmentId;


    private ObservableList<AppointmentsAddModel> appointments = FXCollections.observableArrayList();


    private ObservableList<String> customers = FXCollections.observableArrayList();
    private ObservableList<String> users = FXCollections.observableArrayList();


    private static ObservableList<String> hours = FXCollections.observableArrayList("01",
            "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
            "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    private static ObservableList<String> minutes = FXCollections.observableArrayList("00",
            "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55");
    private static ObservableList<String> meetingTypes = FXCollections.observableArrayList(
            "Planning", "Decision Making", "Progress Update");



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

    public static ObservableList<String> getCustomers() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        return dataSource.customerQuery();
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

    public static ObservableList<String> getUsers() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        return dataSource.userQuery();
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
    
    public boolean conflictsWithExistingAppointment() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<Appointment> appointments = dataSource.queryAppointment();

        for (Appointment appointment : appointments) {
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
        LocalDateTime dateTimeStart = convertToLocalDateTime(this.startHour, this.startMinute);
        LocalDateTime dateTimeEnd = convertToLocalDateTime(this.endHour, this.endMinute);
        if (dateTimeStart.isAfter(dateTimeEnd)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean notWithinBusinessHours() {
        LocalDateTime dateTimeStart = convertToLocalDateTime(this.startHour, this.startMinute);
        LocalDateTime dateTimeEnd = convertToLocalDateTime(this.endHour, this.endMinute);

        LocalTime businessTimeStart = LocalTime.of(8, 0);
        LocalTime businessTimeEnd = LocalTime.of(17, 0);
        int dayOfWeek = dateTimeStart.getDayOfWeek().getValue();

        if (dateTimeStart.toLocalTime().isBefore(businessTimeStart) || dateTimeEnd.toLocalTime().isAfter(businessTimeEnd) ||
                dayOfWeek == 6 || dayOfWeek == 7) {
            return true;
        } else {
            return false;
        }

    }

    private LocalDateTime convertToLocalDateTime(String hour, String minute) {
        return LocalDateTime.of(this.date.getYear(), this.date.getMonthValue(),
                this.date.getDayOfMonth(), Integer.parseInt(hour), Integer.parseInt(minute));
    }


}
