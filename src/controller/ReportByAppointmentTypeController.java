/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ReportByAppointmentTypeModel;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author robertthomure
 */
public class ReportByAppointmentTypeController implements Initializable {
    //ReportByAppointmentTypeModel reportByAppointmentTypeModel = new ReportByAppointmentTypeModel();


    public void switchScenes(ActionEvent event, String view) throws IOException{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/" + view + ".fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private TableView<ReportByAppointmentTypeModel> reportTable;

    @FXML
    private TableColumn<ReportByAppointmentTypeModel, String> monthCol;

    @FXML
    private TableColumn<ReportByAppointmentTypeModel, Integer> numberApptTypeCol;

    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        switchScenes(event, "ReportsMenu");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reportTable.setItems(ReportByAppointmentTypeModel.getReportByAppointmentType());
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        numberApptTypeCol.setCellValueFactory(new PropertyValueFactory<>("count"));
    }
}