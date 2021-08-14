package model;

import database.DataSource;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.time.LocalDateTime;

public class LoginModel {
    private int userId;
    private String userName;
    private String password;
    private LocalDateTime start;
    private LocalDateTime end;
    private  ObservableList<LoginModel> users;
    private ObservableList<LoginModel> appointments;


    public boolean checkIfUsernamePasswordCorrect() {
        for (LoginModel user : users) {
            if (userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkForUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenMinutes = now.plusMinutes(15);
        FilteredList<LoginModel> fifteenMinuteList = new FilteredList<>(appointments);
        fifteenMinuteList.setPredicate(row -> {
            LocalDateTime start = row.getStart();
            return start.isAfter(now) && start.isBefore(fifteenMinutes);
        });
        return !fifteenMinuteList.isEmpty();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public ObservableList<LoginModel> getUsers() {
        return users;
    }

    public void  setUsers() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        this.users = dataSource.loginQuery();
    }

    public void setAppointments() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        this.appointments = dataSource.immediateAppointmentsQuery();
    }


}