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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CustomerRecordsAddModel;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author robertthomure
 */
public class CustomerRecordsAddController implements Initializable {

    public void switchScenes(ActionEvent event, String view) throws IOException{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/" + view + ".fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField cityTxt;

    @FXML
    private TextField countryTxt;

    @FXML
    private TextField phoneNumberTxt;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        switchScenes(event, "CustomerRecordsView");
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        String name = nameTxt.getText().trim();
        String address = addressTxt.getText().trim();
        String city = cityTxt.getText().trim();
        String country = countryTxt.getText().trim();
        String phone = phoneNumberTxt.getText().trim();
        try {
            if(name.isEmpty() || address.isEmpty() || city.isEmpty() || country.isEmpty() || phone.isEmpty()){
                throw new IllegalArgumentException("Please enter info for all fields");
            }
            try {

                Long.parseLong(phone); // used to verify only numbers input for phone#
                CustomerRecordsAddModel newCustomer = new CustomerRecordsAddModel(name, address, city, country, phone);
                newCustomer.addCustomerToDB(newCustomer);
                switchScenes(event, "CustomerRecordsView");
            } catch(NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter numbers for phone number field");
                alert.showAndWait();
            }
        } catch(IllegalArgumentException e) {
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
    }
}