/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package model;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//
//import dataAccess.QueryDB;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//
///**
// *
// * @author robertthomure
// */
//public class AppointmentRecords {
//    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
//
////    public ObservableList<Appointment> getAllAppointments() {
////        return allAppointments;
////    }
//
//
////    public void addAppointment(Appointment appointment) {
////        allAppointments.add(appointment);
////    }
//
//
////    public void createAppointmentList() throws SQLException {
////        ZoneId zoneId = ZoneId.systemDefault();
////        QueryDB query = new QueryDB();
////        //ResultSet appointmentRS = query.selectAllFromTbl("appointment");
////        ResultSet appointmentRS = query.selectAllAppointments();
////        while(appointmentRS.next()) {
////            int customerId = appointmentRS.getInt("customerId");
////            int userId = appointmentRS.getInt("userId");
////            int appointmentId = appointmentRS.getInt("appointmentId");
////            String customer = appointmentRS.getString("customerName");
////            String userName = appointmentRS.getString("userName");
////            String type = appointmentRS.getString("type");
////            //convert timestamp to localTime
////            LocalDateTime dateTimeStart = appointmentRS.getTimestamp("start").toLocalDateTime();
////            ZonedDateTime zoneDateTimeStart = dateTimeStart.atZone(zoneId.of("UTC"));
////            ZonedDateTime localTimeStart = zoneDateTimeStart.withZoneSameInstant(zoneId);
////            LocalDateTime dateTimeEnd = appointmentRS.getTimestamp("end").toLocalDateTime();
////            ZonedDateTime zoneDateTimeEnd = dateTimeEnd.atZone(zoneId.of("UTC"));
////            ZonedDateTime localTimeEnd = zoneDateTimeEnd.withZoneSameInstant(zoneId);
////            LocalDate date = localTimeStart.toLocalDate();
////            LocalTime startTime = localTimeStart.toLocalTime();
////            LocalTime endTime = localTimeEnd.toLocalTime();
////            Appointment row = new Appointment(appointmentId, customerId,
////                    userId, date, startTime, endTime, dateTimeStart, dateTimeEnd,
////                    type, customer, userName);
////
////// fix this
//////            TableLists.addAppointmentRows(row);
////            addAppointment(row);
////        }
////
////    }
//
//}
