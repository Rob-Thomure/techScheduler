/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dataAccess.Data;
import exceptions.ConflictsWithScheduleException;
import exceptions.NotWithinBusinessHoursException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.TableLists;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author robertthomure
 */
public class AppointmentsAddController implements Initializable {
    AppointmentsAddModel appointmentsAddModel = new AppointmentsAddModel();

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
    void onActionCancel(ActionEvent event) throws IOException {
        switchScenes(event, "AppointmentsView");
    }

    //TODO refactor to use DataSource instead of queryDB
    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {
        LocalDate date = dateBox.getValue();
        String startHour = startHrComboBox.getValue();
        String startMinute = startMinuteComboBox.getValue();
        String endHour = endHrComboBox.getValue();
        String endMinute = endMinuteComboBox.getValue();
        appointmentsAddModel.setType(typeComboBox.getValue());

        String customerName = customerComboBox.getValue();
        String userName = userComboBox.getValue();



        try {
            if (date == null || startHour == null || startMinute == null || endHour == null || endMinute == null ||
                    customerName == null || userName == null) {
                throw new NullPointerException();
            }
            //convert date, hour, minute into LocalDateTime format
            LocalDateTime dateTimeStart = LocalDateTime.of(date.getYear(), date.getMonthValue(),
                    date.getDayOfMonth(), Integer.parseInt(startHour), Integer.parseInt(startMinute));
            appointmentsAddModel.setStart(appointmentsAddModel.convertToUTC(dateTimeStart));
            LocalDateTime dateTimeEnd = LocalDateTime.of(date.getYear(), date.getMonthValue(),
                    date.getDayOfMonth(), Integer.parseInt(endHour), Integer.parseInt(endMinute));
            appointmentsAddModel.setEnd(appointmentsAddModel.convertToUTC(dateTimeEnd));
            if(dateTimeStart.isBefore(dateTimeEnd)){
                String startTime = Data.convertToUTC(dateTimeStart);
                String endTime = Data.convertToUTC(dateTimeEnd);
                // set customerId
                appointmentsAddModel.setCustomerId(appointmentsAddModel.getCustomerIdFromDB(customerName));
                // set userId
                appointmentsAddModel.setUserId(appointmentsAddModel.getUserIdFromDB(userName));
                //checking existing appointments
                appointmentsAddModel.setAppointments();
                LocalTime localStartTime = dateTimeStart.toLocalTime();
                LocalTime localEndTime = dateTimeEnd.toLocalTime();
                LocalTime businessTimeStart = LocalTime.of(8, 00);
                LocalTime businessTimeEnd = LocalTime.of(17, 00);
                int dayOfWeek = dateTimeStart.getDayOfWeek().getValue();
                if(appointmentsAddModel.checkForConflictingAppointments()) {
                    throw new ConflictsWithScheduleException("conflicts with an existing schedule");
                }
                if(localStartTime.isBefore(businessTimeStart) || localEndTime.isAfter(businessTimeEnd) ||
                        dayOfWeek == 6 || dayOfWeek == 7){
                    throw new NotWithinBusinessHoursException("Scheduled time is not "
                            + "within business hours (Mon-Fri 08:00 - 17:00)");
                }
                appointmentsAddModel.addAppointmentToDB(appointmentsAddModel);
                switchScenes(event, "AppointmentsView");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please choose an end time that is after the start time");
                alert.showAndWait();
            }
        } catch(NullPointerException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all empty fields");
            alert.showAndWait();
        } catch (ConflictsWithScheduleException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("conflicts with an existing schedule");
            alert.showAndWait();
        } catch (NotWithinBusinessHoursException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please schedule a time within business hours(Mon-Fri 08:00-17:00)");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionTypeCombo(ActionEvent event) {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startHrComboBox.setItems(TableLists.getHours());
        startMinuteComboBox.setItems(TableLists.getMinutes());
        endHrComboBox.setItems(TableLists.getHours());
        endMinuteComboBox.setItems(TableLists.getMinutes());
        typeComboBox.setItems(TableLists.getMeetingTypes());
        //build the customer combo box
        appointmentsAddModel.setCustomers();
        customerComboBox.setItems(appointmentsAddModel.getCustomers());
        //build the user combo box
        appointmentsAddModel.setUsers();
        userComboBox.setItems(appointmentsAddModel.getUsers());
    }
}