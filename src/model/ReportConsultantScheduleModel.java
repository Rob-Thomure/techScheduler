package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReportConsultantScheduleModel {

    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;
    private final String type;
    private final String customerName;
    private final String userName;

    public ReportConsultantScheduleModel(LocalDate date, LocalTime start, LocalTime end, String type,
                                         String customerName, String userName) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.type = type;
        this.customerName = customerName;
        this.userName = userName;
    }

    public static ObservableList<ReportConsultantScheduleModel> getAppointments(String userName) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<Appointment> appointments = dataSource.scheduleForEachConsultantQuery(userName);
        ObservableList<ReportConsultantScheduleModel> observableListAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : appointments) {
            ReportConsultantScheduleModel report = new ReportConsultantScheduleModel(
                    appointment.getStart().toLocalDate(),
                    appointment.getStart().toLocalTime(),
                    appointment.getEnd().toLocalTime(),
                    appointment.getType(),
                    appointment.getCustomer().getCustomerName(),
                    appointment.getUser().getUserName());
            observableListAppointments.add(report);
        }
        return observableListAppointments;
    }

    public static ObservableList<String> buildUserComboBoxList() {
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

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
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

}
