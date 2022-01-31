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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CustomerRecords;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author robertthomure
 */
public class CustomerRecordsViewController implements Initializable {


    public void switchScenes(ActionEvent event, String view) throws IOException{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/" + view + ".fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private TableView<CustomerRecords> customerRecordsTableView;

    @FXML
    private TableColumn<CustomerRecords, String> nameCol;

    @FXML
    private TableColumn<CustomerRecords, String> addressCol;

    @FXML
    private TableColumn<CustomerRecords, String> phoneNumberCol;

    @FXML
    void onActionAdd(ActionEvent event) throws IOException {
        switchScenes(event, "CustomerRecordsAdd");
    }

    @FXML
    void onActionDelete(ActionEvent event) throws IOException {
        try {
            //get selected row on the customer table view
            CustomerRecords customer = customerRecordsTableView.getSelectionModel().getSelectedItem();


            try {
                if (customer.hasAppointmentScheduled(customer.getCustomerName())) {
                    throw new SQLIntegrityConstraintViolationException("has an appointment scheduled");
                }
                customer.deleteCustomerFromDB(customer.getCustomerName());
                switchScenes(event, "CustomerRecordsView");
            } catch(SQLIntegrityConstraintViolationException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("customer has appts scheduled, please delete "
                        + "associated appointments before deleting customer");
                alert.showAndWait();
            }
        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a row to delete");
            alert.showAndWait();
        }
    }

    @FXML
    void OnActionDisplayAppointments(ActionEvent event) throws IOException {
        switchScenes(event, "AppointmentsView");
    }

    @FXML
    void onActionUpdate(ActionEvent event) throws IOException {
        try {
            // transfer data to update screen
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/CustomerRecordsUpdate.fxml"));
            loader.load();
            CustomerRecordsUpdateController cRUController = loader.getController();
            cRUController.sendCustomer(customerRecordsTableView.getSelectionModel().getSelectedItem());
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<CustomerRecords> customers = CustomerRecords.buildCustomerList();
        customerRecordsTableView.setItems(customers);
        nameCol.setCellValueFactory( new PropertyValueFactory<>("customerName") );
        addressCol.setCellValueFactory( new PropertyValueFactory<>("address") );
        phoneNumberCol.setCellValueFactory( new PropertyValueFactory<>("phone") );
    }
}