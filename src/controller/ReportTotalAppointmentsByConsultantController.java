/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
import model.ReportTotalAppointmentsByConsultantModel;

/**
 * FXML Controller class
 *
 * @author robertthomure
 */
public class ReportTotalAppointmentsByConsultantController implements Initializable {

    public void switchScenes(ActionEvent event, String view) throws IOException{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/" + view + ".fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private TableView<ReportTotalAppointmentsByConsultantModel> reportTable;

    @FXML
    private TableColumn<ReportTotalAppointmentsByConsultantModel, String> consultantCol;

    @FXML
    private TableColumn<ReportTotalAppointmentsByConsultantModel, Integer> numberOfAppointmentsCol;

    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        switchScenes(event, "ReportsMenu");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<ReportTotalAppointmentsByConsultantModel> appointmentReports = ReportTotalAppointmentsByConsultantModel.getAppointmentCountReport();
        reportTable.setItems(appointmentReports);
        consultantCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        numberOfAppointmentsCol.setCellValueFactory(new PropertyValueFactory<>("count"));
    }
}
