/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsoftware2projectappver2;

import dataAccess.ConnectDB;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author robertthomure
 */
public class RTSoftware2ProjectAppVer2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ResourceBundle rb = ResourceBundle.getBundle("resourceBundle_files/Language", Locale.getDefault());

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();


//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
//        loader.setResources(rb);
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

//    /**
//     * @param args the command line arguments
//     * @throws java.sql.SQLException
//     */
//    public static void main(String[] args) throws SQLException {
//        ConnectDB.DBConnect();
//        launch(args);
//        ConnectDB.DBClose();
//    }


    public static void main(String[] args) {
        launch(args);
    }
}
