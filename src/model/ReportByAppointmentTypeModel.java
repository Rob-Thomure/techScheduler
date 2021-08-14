package model;

import database.DataSource;
import javafx.collections.ObservableList;

public class ReportByAppointmentTypeModel {
    private String month;
    private int count;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ObservableList<ReportByAppointmentTypeModel> getReportByAppointmentType() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        return (ObservableList<ReportByAppointmentTypeModel>) dataSource.numberAppointmentTypesByMonthQuery();
    }
}
