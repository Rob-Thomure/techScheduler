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
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.AppointmentsUpdateModel;
import model.AppointmentsViewModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author robertthomure
 */
public class AppointmentsUpdateController implements Initializable {
    AppointmentsViewModel appointmentsViewModel;

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
    private TextField appointmentIdTxt;

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
        int appointmentId = appointmentsViewModel.getAppointmentId();

        AppointmentsUpdateModel appointmentUpdate = new AppointmentsUpdateModel(date, startHour, startMinute, endHour,
                endMinute, type, customerName, userName, appointmentId);
        try {
            if (appointmentUpdate.startIsAfterEnd()) {
                throw new StartIsAfterEndException("Please choose an end time that is after the start time");
            }
            if (appointmentUpdate.conflictsWithExistingAppointment()) {
                throw new ConflictsWithScheduleException("conflicts with an existing schedule");
            }
            if (appointmentUpdate.notWithinBusinessHours()) {
                throw new NotWithinBusinessHoursException("Scheduled time is not within business hours (Mon-Fri 08:00 - 17:00)");
            }
            appointmentUpdate.updateAppointment(appointmentUpdate);
            switchScenes(event, "AppointmentsView");
        } catch (StartIsAfterEndException | ConflictsWithScheduleException | NotWithinBusinessHoursException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void sendAppointment(AppointmentsViewModel appointmentRow){
        dateBox.setValue(appointmentRow.getDate());
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");
        startHrComboBox.setValue(appointmentRow.getStart().format(hourFormatter));
        startMinuteComboBox.setValue(appointmentRow.getStart().format(minuteFormatter));
        endHrComboBox.setValue(appointmentRow.getEnd().format(hourFormatter));
        endMinuteComboBox.setValue(appointmentRow.getEnd().format(minuteFormatter));
        typeComboBox.setValue(appointmentRow.getType());
        customerComboBox.setValue(appointmentRow.getCustomerName());
        userComboBox.setValue(appointmentRow.getUserName());
        appointmentIdTxt.setText(String.valueOf(appointmentRow.getAppointmentId()));
        appointmentsViewModel = appointmentRow;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startHrComboBox.setItems(AppointmentsUpdateModel.getHours());
        startMinuteComboBox.setItems(AppointmentsUpdateModel.getMinutes());
        endHrComboBox.setItems(AppointmentsUpdateModel.getHours());
        endMinuteComboBox.setItems(AppointmentsUpdateModel.getMinutes());
        typeComboBox.setItems(AppointmentsUpdateModel.getMeetingTypes());
        customerComboBox.setItems(AppointmentsUpdateModel.setCustomerComboBoxNames());
        userComboBox.setItems(AppointmentsUpdateModel.setUserComboBoxNames());

    }
}