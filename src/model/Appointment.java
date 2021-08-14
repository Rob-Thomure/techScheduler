/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 *
 * @author robertthomure
 */
public class Appointment {


    private int appointmentId;
    private int customerId;
    private int userId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String type;
    private String customerName;
    private String userName;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;

    private LocalDateTime start;
    private LocalDateTime end;
    private Customer customer;
    private User user;

    public Appointment(int appointmentId, String type, LocalDateTime start, LocalDateTime end,
                       Customer customer, User user) {
        this.appointmentId = appointmentId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer = customer;
        this.user = user;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Appointment(int appointmentId, int customerId, int userId, LocalDate date,
                       LocalTime startTime, LocalTime endTime, LocalDateTime dateTimeStart,
                       LocalDateTime dateTimeEnd, String type, String customer, String userName) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.customerName = customer;
        this.userName = userName;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
    }







    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void addAppointment(){

    }

    public void updateAppointment(){

    }

    public void deleteAppointment(){

    }


}