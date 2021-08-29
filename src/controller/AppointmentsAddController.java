/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.ConflictsWithScheduleException;
import exceptions.NotWithinBusinessHoursException;
import exceptions.StartIsAfterEndException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.AppointmentsAddModel;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author robertthomure
 */
public class AppointmentsAddController implements Initializable {

    public void switchScenes(ActionEvent event, String view) throws IOException{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/" + view + ".fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private DatePicker dateBox;

    @FXML
    private ComboBox<String> startHrComboBox;

    @FXML
    private ComboBox<String> startMinuteComboBox;

    @FXML
    private ComboBox<String> endHrComboBox;

    @FXML
    private ComboBox<String> endMinuteComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<String> customerComboBox;

    @FXML
    private ComboBox<String> userComboBox;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        switchScenes(event, "AppointmentsView");
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        LocalDate date = dateBox.getValue();
        String startHour = startHrComboBox.getValue();
        String startMinute = startMinuteComboBox.getValue();
        String endHour = endHrComboBox.getValue();
        String endMinute = endMinuteComboBox.getValue();
        String type = typeComboBox.getValue();
        String customerName = customerComboBox.getValue();
        String userName = userComboBox.getValue();
        try {
            if (date == null || startHour == null || startMinute == null || endHour == null || endMinute == null ||
                    customerName == null || type == null || userName == null) {
                throw new NullPointerException("Please fill in all empty fields");
            }

            AppointmentsAddModel newAppointment = new AppointmentsAddModel(date, startHour, startMinute, endHour,
                    endMinute, type, customerName, userName);
            if(newAppointment.startIsAfterEnd()) {
                throw new StartIsAfterEndException("Please choose an end time that is after the start time");
            }
            if(newAppointment.notWithinBusinessHours()){
                throw new NotWithinBusinessHoursException("Please schedule a time within business hours(Mon-Fri 08:00-17:00)");
            }
            if(newAppointment.conflictsWithExistingAppointment()) {
                throw new ConflictsWithScheduleException("conflicts with an existing schedule");
            }
            newAppointment.addAppointmentToDB(newAppointment);
            switchScenes(event, "AppointmentsView");

        } catch(NullPointerException | StartIsAfterEndException | NumberFormatException | ConflictsWithScheduleException
                | NotWithinBusinessHoursException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startHrComboBox.setItems(AppointmentsAddModel.getHours());
        startMinuteComboBox.setItems(AppointmentsAddModel.getMinutes());
        endHrComboBox.setItems(AppointmentsAddModel.getHours());
        endMinuteComboBox.setItems(AppointmentsAddModel.getMinutes());
        typeComboBox.setItems(AppointmentsAddModel.getMeetingTypes());
        customerComboBox.setItems(AppointmentsAddModel.getCustomers());
        userComboBox.setItems(AppointmentsAddModel.getUsers());
    }
}