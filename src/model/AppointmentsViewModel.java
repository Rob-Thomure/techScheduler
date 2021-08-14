package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentsViewModel {

    private int appointmentId;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private String type;
    private String customerName;
    private String userName;
    private ObservableList<AppointmentsViewModel> appointments = FXCollections.observableArrayList();

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
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

    public ObservableList<AppointmentsViewModel> getAppointments() {
        return appointments;
    }

    public void setAppointments() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        this.appointments = dataSource.appointmentsQuery();
    }

    public void deleteAppointmentFromDB(int appointmentId) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        dataSource.deleteFromTbl("appointment", "appointment", appointmentId);
    }

}
