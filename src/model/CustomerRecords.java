/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author robertthomure
 */
public class CustomerRecords {

    // removing static
    public int retrieveCustomerId(String customerName, ResultSet customerRS) throws SQLException  {
        int customerId = -1;
        while (customerRS.next()) {
            if (customerName.equals(customerRS.getString("customerName"))) {
                customerId = customerRS.getInt("customerId");
            }
        }
        return customerId;
    }

}
