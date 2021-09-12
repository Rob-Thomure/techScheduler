package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReportTotalAppointmentsByConsultantModel {
    private final String userName;
    private final int count;

    public ReportTotalAppointmentsByConsultantModel(String userName, int count) {
        this.userName = userName;
        this.count = count;
    }

    public static ObservableList<ReportTotalAppointmentsByConsultantModel> getAppointmentCountReport() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        return FXCollections.observableArrayList(dataSource.appointmentsForEachConsultantQuery());
    }

    public String getUserName() {
        return userName;
    }

    public int getCount() {
        return count;
    }
}
