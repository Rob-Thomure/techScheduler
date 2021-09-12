/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author robertthomure
 */
public class ReportTotalAppointments {
    private final String consultant;
    private final int numberOfAppointments;

    public ReportTotalAppointments(String consultant, int numberOfAppointments) {
        this.consultant = consultant;
        this.numberOfAppointments = numberOfAppointments;
    }

    public String getConsultant() {
        return consultant;
    }

    public int getNumberOfAppointments() {
        return numberOfAppointments;
    }
}