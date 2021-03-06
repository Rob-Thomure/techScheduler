/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

//import dataAccess.ConnectDB;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.LoginModel;
import model.NewScene;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author robertthomure
 */
public class LoginController implements Initializable {

    ResourceBundle rb;

    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Label titleLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    @FXML
    void onActionExit() {
        LoginModel.exit();
    }

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        try {
            LoginModel login = new LoginModel(usernameTxt.getText(), passwordTxt.getText());
            if (login.userNamePasswordCorrect()) {
                if (login.hasUpcomingAppointments()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert!");
                    alert.setHeaderText("Appointment Alert");
                    alert.setContentText("There is an appointment within 15 minutes");
                    alert.showAndWait();
                }
                login.recordLogin(usernameTxt.getText(), "successfully");
                NewScene newScene = new NewScene(event, "AppointmentsView");
                newScene.switchScenes();
            } else {
                login.recordLogin(usernameTxt.getText(), "failed");
                throw new IllegalArgumentException("incorrect username or password");
            }
        } catch(IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, rb.getString("message"));
            alert.setTitle(rb.getString("warning"));
            alert.setHeaderText(rb.getString("warning"));
            // Lambda expression used to display alert message efficiently
            alert.showAndWait().filter(response -> response == (ButtonType.OK)     );
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        titleLabel.setText(rb.getString("title"));
        userNameLabel.setText(rb.getString("userName"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
    }
}