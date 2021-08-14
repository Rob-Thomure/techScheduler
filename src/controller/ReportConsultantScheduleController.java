/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ReportConsultantScheduleModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author robertthomure
 */
public class ReportConsultantScheduleController implements Initializable {
    ReportConsultantScheduleModel reportConsultantScheduleModel = new ReportConsultantScheduleModel();


    public void switchScenes(ActionEvent event, String view) throws IOException{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/" + view + ".fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private TableView<ReportConsultantScheduleModel> reportTable;

    @FXML
    private TableColumn<ReportConsultantScheduleModel, LocalDate> dateCol;

    @FXML
    private TableColumn<ReportConsultantScheduleModel, LocalTime> startTimeCol;

    @FXML
    private TableColumn<ReportConsultantScheduleModel, LocalTime> endTimeCol;

    @FXML
    private TableColumn<ReportConsultantScheduleModel, String> typeCol;

    @FXML
    private TableColumn<ReportConsultantScheduleModel, String> customerCol;

    @FXML
    private TableColumn<ReportConsultantScheduleModel, String> consultantCol;

    @FXML
    private ComboBox<String> consultantComboBox;

    @FXML
    void onActionconsultantComboBox(ActionEvent event) throws SQLException {
        String consultant = consultantComboBox.getValue();
        ObservableList<ReportConsultantScheduleModel> appointments = reportConsultantScheduleModel.getAppointments();
        reportTable.setItems(appointments);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        consultantCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
    }

    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        switchScenes(event, "ReportsMenu");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        consultantComboBox.setItems(reportConsultantScheduleModel.buildUserComboBoxList());
    }
}