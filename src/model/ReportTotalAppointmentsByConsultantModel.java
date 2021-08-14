package model;

import database.DataSource;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ReportTotalAppointmentsByConsultantModel {
    private String userName;
    private int count;

    public ObservableList<ReportTotalAppointmentsByConsultantModel> getAppointmentCountReport() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<ReportTotalAppointmentsByConsultantModel> reportTotalAppointmentsByConsultantModels = dataSource.appointmentsForEachConsultantQuery();
        return (ObservableList<ReportTotalAppointmentsByConsultantModel>) reportTotalAppointmentsByConsultantModels;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
