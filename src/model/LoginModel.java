package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class LoginModel {
    private final String userName;
    private final String password;

    public LoginModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public boolean userNamePasswordCorrect() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<User> users = dataSource.queryUser();
        for (User user : users) {
            if (this.userName.equals(user.getUserName()) && this.password.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasUpcomingAppointments() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList(dataSource.queryAppointment()) ;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenMinutes = now.plusMinutes(15);
        FilteredList<Appointment> fifteenMinuteList = new FilteredList<>(appointments);
        fifteenMinuteList.setPredicate(row -> {
            LocalDateTime start = row.getStart();
            return start.isAfter(now) && start.isBefore(fifteenMinutes);
        });
        return !fifteenMinuteList.isEmpty();
    }

    public void recordLogin(String userName, String result){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zoneDateTime = LocalDateTime.now().atZone(zoneId).withZoneSameInstant(ZoneId.of("UTC"));
        try (PrintWriter loginLogger = new PrintWriter(new FileOutputStream(
                "login_log.txt", true))) {
            loginLogger.append(userName).append(" ").append(result).append(" logged in ").append(String.valueOf(zoneDateTime)).append("\n");
        } catch (FileNotFoundException ex) {
            System.out.println("login_log.txt not found");
        }
    }

    public static void exit() {
        DataSource dataSource = DataSource.getInstance();
        if (dataSource != null) {
            dataSource.closeConnection();
        }
        System.exit(0);
    }
}
