/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

//import dataAccess.QueryDB;
import database.DataSource;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author robertthomure
 */
public class User {
//    private QueryDB query = new QueryDB();

    private String userName;
    private String password;

    private int userId;

    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

//    public void login(ActionEvent event, ResourceBundle rb) throws SQLException, IOException {
//        try {
//            boolean credentialsMatch = checkLoginCredentials();
//            if (credentialsMatch) {
//                boolean immediateAppointment = checkForImmediateAppointments();
//                if (immediateAppointment) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Alert!");
//                    alert.setHeaderText("Appointment Alert");
//                    alert.setContentText("There is an appointment within 15 minutes");
//                    alert.showAndWait();
//                }
//                Data.recordLogin(userName, "successfully");
//                NewScene newScene = new NewScene(event, "AppointmentsView");
//                newScene.switchScenes();
//            }
//            if (!credentialsMatch) {
//                Data.recordLogin(userName, "failed");
//                throw new IllegalArgumentException("incorrect username or password");
//            }
//        } catch(IllegalArgumentException e) {
//            Alert alert = new Alert(Alert.AlertType.WARNING, rb.getString("message"));
//            alert.setTitle(rb.getString("warning"));
//            alert.setHeaderText(rb.getString("warning"));
//            // Lambda expression used to display alert message efficiently
//            alert.showAndWait().filter(response -> response == (ButtonType.OK)     );
//        }
//    }

//    public boolean checkLoginCredentials() {
//        DataSource dataSource = DataSource.getInstance();
//        dataSource.getConnection();
//        ObservableList<User> users = dataSource.queryUser();
//        for (User user : users) {
//            if (userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
//                return true;
//            }
//        }
//        return false;
//    }

//    public boolean checkForImmediateAppointments() {
//        DataSource dataSource = DataSource.getInstance();
//        dataSource.getConnection();
//        ObservableList<Appointment> appointments = dataSource.queryAppointment();
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime fifteenMinutes = now.plusMinutes(15);
//        FilteredList<Appointment> fifteenMinuteList = new FilteredList<>(appointments);
//        fifteenMinuteList.setPredicate(row -> {
//            LocalDateTime start = row.getStart();
//            return start.isAfter(now) && start.isBefore(fifteenMinutes);
//        });
//        return !fifteenMinuteList.isEmpty();
//    }

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
}
