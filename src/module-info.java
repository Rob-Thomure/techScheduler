module JavaFxApplication {
    requires javafx.fxml;
    requires javafx.controls;

   // requires javafx.media; // just added this

    requires java.sql;

    opens model;
    opens main;
    opens controller;
}