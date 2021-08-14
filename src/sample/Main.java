package sample;

import dataAccess.ConnectDB;
import database.DataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        ResourceBundle rb = ResourceBundle.getBundle("resourceBundle_files/Language", Locale.getDefault());
        //Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        loader.setResources(rb);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();


    }



    public static void main(String[] args) throws SQLException {
        DataSource dataSource = DataSource.getInstance();

        launch(args);
        if (dataSource != null) {
            dataSource.closeConnection();
        }
    }
}
