/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import exceptions.ConflictsWithScheduleException;
import exceptions.NotWithinBusinessHoursException;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class
 *
 * @author robertthomure
 */
public class AppointmentsUpdateController implements Initializable {
    AppointmentsUpdateModel appointmentsUpdateModel = new AppointmentsUpdateModel();

    public void switchScenes(ActionEvent event, String view) throws IOException{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/" + view + ".fxml"));
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
    void onActionSave(ActionEvent event) throws IOException, SQLException {
        LocalDate date = dateBox.getValue();
        String startHour = startHrComboBox.getValue();
        String startMinute = startMinuteComboBox.getValue();
        String endHour = endHrComboBox.getValue();
        String endMinute = endMinuteComboBox.getValue();
        String type = typeComboBox.getValue();
        String customerName = customerComboBox.getValue();
        String userName = userComboBox.getValue();
        int customerId = appointmentsUpdateModel.getCustomerIdFromCustomers(customerName);
        int userId = appointmentsUpdateModel.getUserIdFromCustomers(userName);
        LocalTime startTime = LocalTime.of(Integer.parseInt(startHour), Integer.parseInt(startMinute));
        LocalTime endTime = LocalTime.of(Integer.parseInt(endHour), Integer.parseInt(endMinute));
        LocalDateTime dateTimeStart = LocalDateTime.of(date.getYear(), date.getMonthValue(),
                date.getDayOfMonth(), Integer.parseInt(startHour), Integer.parseInt(startMinute));
        LocalDateTime dateTimeEnd = LocalDateTime.of(date.getYear(), date.getMonthValue(),
                date.getDayOfMonth(), Integer.parseInt(endHour), Integer.parseInt(endMinute));
        if(dateTimeStart.isBefore(dateTimeEnd)) {
            appointmentsUpdateModel.setCustomerId(customerId);
            appointmentsUpdateModel.setUserId(userId);
            appointmentsUpdateModel.setType(type);
            appointmentsUpdateModel.setLocalDateTimeStart(appointmentsUpdateModel.convertToUTC(dateTimeStart));
            appointmentsUpdateModel.setLocalDateTimeEnd(appointmentsUpdateModel.convertToUTC(dateTimeEnd));
            int dayOfWeek = dateTimeStart.getDayOfWeek().getValue();
            LocalTime businessTimeStart = LocalTime.of(8, 00);
            LocalTime businessTimeEnd = LocalTime.of(17, 00);
            try {
                if(appointmentsUpdateModel.checkForConflictingAppointments()) {
                    throw new ConflictsWithScheduleException("conflicts with an existing schedule");
                }
                if (startTime.isBefore(businessTimeStart) || endTime.isAfter(businessTimeEnd) ||
                        dayOfWeek == 6 || dayOfWeek == 7) {
                    throw new NotWithinBusinessHoursException("Scheduled time is not within business "
                            + "hours (Mon-Fri 08:00 - 17:00)");
                    }
                appointmentsUpdateModel.updateAppointment(appointmentsUpdateModel);
                switchScenes(event, "AppointmentsView");
            }
            catch(NotWithinBusinessHoursException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please schedule a time within business hours(Mon-Fri 08:00-17:00)");
                alert.showAndWait();
            }
            catch (ConflictsWithScheduleException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("conflicts with an existing schedule");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please choose an end time that is after the start time");
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

        appointmentsUpdateModel.setDate(appointmentRow.getDate());
        appointmentsUpdateModel.setStart(appointmentRow.getStart());
        appointmentsUpdateModel.setEnd(appointmentRow.getEnd());
        appointmentsUpdateModel.setType(appointmentRow.getType());
        appointmentsUpdateModel.setCustomerName(appointmentRow.getCustomerName());
        appointmentsUpdateModel.setUserName(appointmentRow.getUserName());
        appointmentsUpdateModel.setAppointmentId(appointmentRow.getAppointmentId());

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentsUpdateModel.setAppointments();
        appointmentsUpdateModel.setCustomers();
        appointmentsUpdateModel.setCustomersComboBox();
        appointmentsUpdateModel.setUsers();
        appointmentsUpdateModel.setUsersComboBox();

        startHrComboBox.setItems(TableLists.getHours());
        startMinuteComboBox.setItems(TableLists.getMinutes());
        endHrComboBox.setItems(TableLists.getHours());
        endMinuteComboBox.setItems(TableLists.getMinutes());
        typeComboBox.setItems(TableLists.getMeetingTypes());
        customerComboBox.setItems(appointmentsUpdateModel.getCustomersComboBox());
        userComboBox.setItems(appointmentsUpdateModel.getUsersComboBox());
    }
}