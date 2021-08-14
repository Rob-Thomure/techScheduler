package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReportConsultantScheduleModel {

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private String type;
    private String customerName;
    private String userName;

    public ObservableList<ReportConsultantScheduleModel> getAppointments() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<Appointment> appointments = dataSource.scheduleForEachConsultantQuery(this.userName);
        ObservableList<ReportConsultantScheduleModel> observableListAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : appointments) {
            ReportConsultantScheduleModel reportConsultantScheduleModel = new ReportConsultantScheduleModel();
            reportConsultantScheduleModel.setDate(appointment.getStart().toLocalDate());
            reportConsultantScheduleModel.setStart(appointment.getStart().toLocalTime());
            reportConsultantScheduleModel.setEnd(appointment.getEnd().toLocalTime());
            reportConsultantScheduleModel.setType(appointment.getType());
            reportConsultantScheduleModel.setCustomerName(appointment.getCustomerName());
            reportConsultantScheduleModel.setUserName(appointment.getUserName());
            observableListAppointments.add(reportConsultantScheduleModel);
        }
        return observableListAppointments;
    }






    public ObservableList<String> buildUserComboBoxList() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<User> users = dataSource.queryUser();
        ObservableList<String> usersList = FXCollections.observableArrayList();
        for (User user : users) {
            usersList.add(user.getUserName());
        }
        return usersList;
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
}
