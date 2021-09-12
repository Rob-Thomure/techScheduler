package model;

import database.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class AppointmentsViewModel {

    private final int appointmentId;
    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;
    private final String type;
    private final String customerName;
    private final String userName;

    public AppointmentsViewModel(int appointmentId, LocalDate date, LocalTime start, LocalTime end, String type,
                                 String customerName, String userName) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.start = start;
        this.end = end;
        this.type = type;
        this.customerName = customerName;
        this.userName = userName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public String getType() {
        return type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getUserName() {
        return userName;
    }

    public static ObservableList<AppointmentsViewModel> getAppointments() {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        ArrayList<Appointment> appointments = dataSource.queryAppointment();
        return buildAppointmentsViewList(appointments);
    }

    private static ObservableList<AppointmentsViewModel> buildAppointmentsViewList(ArrayList<Appointment> appointments) {
        ObservableList<AppointmentsViewModel> appointmentsViewList = FXCollections.observableArrayList();
        for (Appointment appointment: appointments) {
            AppointmentsViewModel appointmentsViewModel = new AppointmentsViewModel(
                    appointment.getAppointmentId(),
                    appointment.getStart().toLocalDate(),
                    appointment.getStart().toLocalTime(),
                    appointment.getEnd().toLocalTime(),
                    appointment.getType(),
                    appointment.getCustomer().getCustomerName(),
                    appointment.getUser().getUserName());
            appointmentsViewList.add(appointmentsViewModel);
        }
        return appointmentsViewList;
    }

    public static void deleteAppointmentFromDB(int appointmentId) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getConnection();
        dataSource.deleteFromTbl("appointment", "appointment", appointmentId);
    }

    public static void exit() {
        DataSource dataSource = DataSource.getInstance();
        if (dataSource != null) {
            dataSource.closeConnection();
        }
        System.exit(0);
    }

}
