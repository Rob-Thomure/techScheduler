package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReportByAppointmentTypeModel {
    private final String month;
    private final int count;

    public ReportByAppointmentTypeModel(String month, int count) {
        this.month = month;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public int getCount() {
        return count;
    }

    public static ObservableList<ReportByAppointmentTypeModel> getReportByAppointmentType() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        return FXCollections.observableArrayList(dataSource.numberAppointmentTypesByMonthQuery());
    }
}
