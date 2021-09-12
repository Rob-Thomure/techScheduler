/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author robertthomure
 */
public class Appointment {

    private final int appointmentId;
    private final String type;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final Customer customer;
    private final User user;

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

    public LocalDateTime getEnd() {
        return end;
    }

    public User getUser() {
        return user;
    }

    public Customer getCustomer() {
        return customer;
    }


    public int getAppointmentId() {
        return appointmentId;
    }

    public String getType() {
        return type;
    }

}