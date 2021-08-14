/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;


//import com.mysql.jdbc.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author robertthomure
 */
public class ConnectDB {
    private static final String JDBCURL = "jdbc:mysql://3.227.166.251/U04ltA";
    private static final String DRIVERINTERFACE = "com.mysql.jdbc.Driver";
    private static Connection connection = null;
    private static final String USERNAME = "U04ltA";
    private static final String PASSWORD = "53688278090";

    public static Connection DBConnect() {
        try{
            Class.forName(DRIVERINTERFACE);
            connection = (Connection)DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void DBClose() {
        try{
            if (connection != null) {
                connection.close();
            }

        }
        catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}