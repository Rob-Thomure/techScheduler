/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.AppointmentsViewModel;
import model.NewScene;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author robertthomure
 */
public class AppointmentsViewController implements Initializable {

    @FXML
    private TableView<AppointmentsViewModel> appointmentsTableView;

    @FXML
    private TableColumn<Appointment, Integer> idCol;

    @FXML
    private TableColumn<Appointment, LocalDate> dateCol;

    @FXML
    private TableColumn<Appointment, LocalTime> startTimeCol;

    @FXML
    private TableColumn<Appointment, LocalTime> endTimeCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> customerCol;

    @FXML
    private TableColumn<Appointment, String> consultantCol;

    @FXML
    void onActionMonthView() {
        LocalDate today = LocalDate.now();
        LocalDate oneMonth = today.plusMonths(1);
        FilteredList<AppointmentsViewModel> monthViewList = new FilteredList<>(AppointmentsViewModel.getAppointments());
        // Lambda expression used to filter appointments efficiently
        monthViewList.setPredicate(row -> {
            LocalDate day = row.getDate() ;
            return day.isAfter(today) && day.isBefore(oneMonth);
        });
        appointmentsTableView.setItems(monthViewList);
    }

    @FXML
    void onActionViewAll() {
        appointmentsTableView.setItems(AppointmentsViewModel.getAppointments());
    }

    @FXML
    void onActionWeekView() {
        LocalDate today = LocalDate.now();
        LocalDate oneWeek = today.plusDays(7);
        FilteredList<AppointmentsViewModel> weekViewList = new FilteredList<>(AppointmentsViewModel.getAppointments());
        // Lambda expression used to filter appointments efficiently
        weekViewList.setPredicate(row -> {
            LocalDate day = row.getDate() ;
            return day.isAfter(today) && day.isBefore(oneWeek);
        });
        appointmentsTableView.setItems(weekViewList);
    }

    @FXML
    void onActionAdd(ActionEvent event) throws IOException {
        NewScene newScene = new NewScene(event, "AppointmentsAdd");
        newScene.switchScenes();
    }

    @FXML
    void onActionDelete(ActionEvent event) throws IOException {
        try {
            AppointmentsViewModel appointmentRow = appointmentsTableView.getSelectionModel().getSelectedItem();
            int appointmentId = appointmentRow.getAppointmentId();
            AppointmentsViewModel.deleteAppointmentFromDB(appointmentId);
            //reload the scene to show that row has been removed from table
            NewScene newScene = new NewScene(event, "AppointmentsView");
            newScene.switchScenes();
        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a row to delete");
            alert.showAndWait();
        }
    }

    @FXML
    void OnActionDisplayCustomerRecords(ActionEvent event) throws IOException {
        NewScene newScene = new NewScene(event, "CustomerRecordsView");
        newScene.switchScenes();
    }

    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        NewScene newScene = new NewScene(event, "ReportsMenu");
        newScene.switchScenes();
    }

    @FXML
    void onActionUpdate(ActionEvent event) throws IOException {
        // transfer data to update screen
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AppointmentsUpdate.fxml"));
        loader.load();
        AppointmentsUpdateController aUController = loader.getController();
        try {
            aUController.sendAppointment(appointmentsTableView.getSelectionModel().getSelectedItem());
            //switch scene
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a row to update");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionExit() {
        AppointmentsViewModel.exit();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentsTableView.setItems(AppointmentsViewModel.getAppointments());
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        consultantCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
    }
}