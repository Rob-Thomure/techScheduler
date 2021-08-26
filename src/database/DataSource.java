package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;

public class DataSource {
    public static final String TABLE_COUNTRY = "country";
    public static final String COLUMN_COUNTRY_COUNTRY_ID = "countryId";
    public static final String COLUMN_COUNTRY_COUNTRY = "country";
    public static final String COLUMN_COUNTRY_CREATE_DATE = "createDate";
    public static final String COLUMN_COUNTRY_CREATED_BY = "createdBy";
    public static final String COLUMN_COUNTRY_LAST_UPDATE = "lastUpdate";
    public static final String COLUMN_COUNTRY_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String TABLE_CITY = "city";
    public static final String COLUMN_CITY_CITY_ID = "cityId";
    public static final String COLUMN_CITY_CITY = "city";
    public static final String COLUMN_CITY_COUNTRY_ID = "countryId";
    public static final String COLUMN_CITY_CREATE_DATE = "createDate";
    public static final String COLUMN_CITY_CREATED_BY = "createdBy";
    public static final String COLUMN_CITY_LAST_UPDATE = "lastUpdate";
    public static final String COLUMN_CITY_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String TABLE_ADDRESS = "address";
    public static final String COLUMN_ADDRESS_ADDRESS_ID = "addressId";
    public static final String COLUMN_ADDRESS_ADDRESS = "address";
    public static final String COLUMN_ADDRESS_ADDRESS2 = "address2";
    public static final String COLUMN_ADDRESS_CITY_ID = "cityId";
    public static final String COLUMN_ADDRESS_POSTAL_CODE = "postalCode";
    public static final String COLUMN_ADDRESS_PHONE = "phone";
    public static final String COLUMN_ADDRESS_CREATE_DATE = "createDate";
    public static final String COLUMN_ADDRESS_CREATED_BY = "createdBy";
    public static final String COLUMN_ADDRESS_LAST_UPDATE = "lastUpdate";
    public static final String COLUMN_ADDRESS_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String TABLE_CUSTOMER = "customer";
    public static final String COLUMN_CUSTOMER_CUSTOMER_ID = "customerId";
    public static final String COLUMN_CUSTOMER_CUSTOMER_NAME = "customerName";
    public static final String COLUMN_CUSTOMER_ADDRESS_ID = "addressId";
    public static final String COLUMN_CUSTOMER_ACTIVE = "active";
    public static final String COLUMN_CUSTOMER_CREATE_DATE = "createDate";
    public static final String COLUMN_CUSTOMER_CREATED_BY = "createdBy";
    public static final String COLUMN_CUSTOMER_LAST_UPDATE = "lastUpdate";
    public static final String COLUMN_CUSTOMER_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String TABLE_APPOINTMENT = "appointment";
    public static final String COLUMN_APPOINTMENT_APPOINTMENT_ID = "appointmentId";
    public static final String COLUMN_APPOINTMENT_CUSTOMER_ID = "customerId";
    public static final String COLUMN_APPOINTMENT_USERID = "userId";
    public static final String COLUMN_APPOINTMENT_TITLE = "title";
    public static final String COLUMN_APPOINTMENT_DESCRIPTION = "description";
    public static final String COLUMN_APPOINTMENT_LOCATION = "location";
    public static final String COLUMN_APPOINTMENT_CONTACT = "contact";
    public static final String COLUMN_APPOINTMENT_TYPE = "type";
    public static final String COLUMN_APPOINTMENT_URL = "url";
    public static final String COLUMN_APPOINTMENT_START = "start";
    public static final String COLUMN_APPOINTMENT_END = "end";
    public static final String COLUMN_APPOINTMENT_CREATE_DATE = "createDate";
    public static final String COLUMN_APPOINTMENT_CREATED_BY = "createdBy";
    public static final String COLUMN_APPOINTMENT_LAST_UPDATE = "lastUpdate";
    public static final String COLUMN_APPOINTMENT_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_USER_ID = "userId";
    public static final String COLUMN_USER_USER_NAME = "userName";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_ACTIVE = "active";
    public static final String COLUMN_USER_CREATE_DATE = "createDate";
    public static final String COLUMN_USER_CREATED_BY = "createdBy";
    public static final String COLUMN_USER_LAST_UPDATE = "lastUpdate";
    public static final String COLUMN_USER_LAST_UPDATE_BY = "lastUpdateBy";

    private static final String JDBCURL = "jdbc:mysql://3.227.166.251/U04ltA";
    private static final String DRIVERINTERFACE = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "U04ltA";
    private static final String PASSWORD = "53688278090";

    private static volatile DataSource instance = null;
    private static volatile Connection connection = null;

    private DataSource() {
        if (instance != null) {
            throw  new RuntimeException("Use getInstance() method to create");
        }
    }

    public static DataSource getInstance() {
        if (instance == null) {
            synchronized (DataSource.class) {
                if (instance == null) {
                    instance = new DataSource();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            synchronized (DataSource.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }


    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Database Close Error: " + e.getMessage());
        }
    }

    public ObservableList<LoginModel> loginQuery() {
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_USER);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            ObservableList<LoginModel> loginAccounts = FXCollections.observableArrayList();
            while (resultSet.next()) {
                LoginModel login = new LoginModel();
                login.setUserId(resultSet.getInt(COLUMN_USER_USER_ID));
                login.setUserName(resultSet.getString(COLUMN_USER_USER_NAME));
                login.setPassword(resultSet.getString(COLUMN_USER_PASSWORD));
                loginAccounts.add(login);
            }
            return loginAccounts;
        } catch(SQLException e) {
            System.out.println("DataSource.queryLogin failed " + e.getMessage());
        }
        return null;
    }

    public ObservableList<LoginModel> immediateAppointmentsQuery() {
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_APPOINTMENT);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            ObservableList<LoginModel> appointments = FXCollections.observableArrayList();
            while (resultSet.next()) {
                LoginModel appointment = new LoginModel();
                appointment.setStart(convertToZonedLocalDateTime(resultSet.getTimestamp(COLUMN_APPOINTMENT_START)));
                appointment.setEnd(convertToZonedLocalDateTime(resultSet.getTimestamp(COLUMN_APPOINTMENT_END)));
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            System.out.println("immediateAppointmentQuery failed " + e.getMessage());
        }
        return null;
    }

    public ObservableList<AppointmentsViewModel> appointmentsQuery() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT appointment.appointmentId, " +
                "appointment.customerId, appointment.userId, customer.customerName, user.userName, appointment.type, " +
                "appointment.start, appointment.end " +
                "FROM appointment " +
                "INNER JOIN customer ON appointment.customerId = customer.customerId " +
                "INNER JOIN user ON appointment.userId = user.userId");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            ObservableList<AppointmentsViewModel> appointments = FXCollections.observableArrayList();
            while (resultSet.next()) {
                AppointmentsViewModel appointmentsViewModel = new AppointmentsViewModel();
                appointmentsViewModel.setAppointmentId(resultSet.getInt(COLUMN_APPOINTMENT_APPOINTMENT_ID));
                appointmentsViewModel.setDate(LocalDate.from(convertToZonedLocalDateTime(resultSet.getTimestamp(COLUMN_APPOINTMENT_START))));
                appointmentsViewModel.setStart(LocalTime.from(convertToZonedLocalDateTime(resultSet.getTimestamp(COLUMN_APPOINTMENT_START))));
                appointmentsViewModel.setEnd(LocalTime.from(convertToZonedLocalDateTime(resultSet.getTimestamp(COLUMN_APPOINTMENT_END))));
                appointmentsViewModel.setType(resultSet.getString(COLUMN_APPOINTMENT_TYPE));
                appointmentsViewModel.setCustomerName(resultSet.getString(COLUMN_CUSTOMER_CUSTOMER_NAME));
                appointmentsViewModel.setUserName(resultSet.getString(COLUMN_USER_USER_NAME));
                appointments.add(appointmentsViewModel);
            }
            return appointments;
        } catch (SQLException e) {
            System.out.println("appointmentsQuery failed " + e.getMessage());
        }
        return null;
    }

    public void deleteFromTbl(String tbl, String tblColumn,int id){
        String deleteStatement = "DELETE FROM " +tbl + " WHERE " + tblColumn + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement)){
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("delete from table " + tbl + " failed " + e.getMessage());
        }
    }

    public ObservableList<String> customerQuery() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_CUSTOMER);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            ObservableList<String> customers = FXCollections.observableArrayList();
            while (resultSet.next()) {
                customers.add(resultSet.getString("customerName"));
            }
            return customers;
        } catch (SQLException e) {
            System.out.println("customerQuery failed " + e.getMessage());
        }
        return null;
    }

    public ObservableList<String> userQuery() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_USER);
            ResultSet resultSet = preparedStatement.executeQuery()){
            ObservableList users = FXCollections.observableArrayList();
            while (resultSet.next()) {
                users.add(resultSet.getString(COLUMN_USER_USER_NAME));
            }
            return users;
        } catch (SQLException e) {
            System.out.println("userQuery failed " + e.getMessage());
        }
        return null;
    }

    public int customerIdQuery(String customerName) {
        int customerId = -1;
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT customerId " +
                       "FROM customer " +
                       "WHERE customerName = ?")) { // ?
            preparedStatement.setString(1, customerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            customerId = resultSet.getInt(COLUMN_CUSTOMER_CUSTOMER_ID);
            return customerId;
        } catch (SQLException e) {
            System.out.println("customerIdQuery failed " + e.getMessage());
        }
        return customerId;
    }


    public int userIdQuery(String userName) {
        int userId = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT userId " +
                        "FROM user " +
                        "WHERE userName = ?")) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userId = resultSet.getInt(COLUMN_USER_USER_ID);
            return userId;
        } catch (SQLException e) {
            System.out.println("userIdQuery failed " + e.getMessage());
        }
        return userId;
    }

//    public ObservableList<AppointmentsAddModel> conflictingAppointmentsQuery() {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(
//                "SELECT * FROM appointment");
//                ResultSet resultSet = preparedStatement.executeQuery()) {
//            ObservableList<AppointmentsAddModel> appointments = FXCollections.observableArrayList();
//            while (resultSet.next()) {
//                AppointmentsAddModel appointmentsAddModel = new AppointmentsAddModel();
//                appointmentsAddModel.setCustomerId(resultSet.getInt(COLUMN_APPOINTMENT_CUSTOMER_ID));
//                appointmentsAddModel.setUserId(resultSet.getInt(COLUMN_APPOINTMENT_USERID));
//                appointmentsAddModel.setStart(convertToZonedLocalDateTime(resultSet.getTimestamp(COLUMN_APPOINTMENT_START)));
//                appointmentsAddModel.setEnd(convertToZonedLocalDateTime(resultSet.getTimestamp(COLUMN_APPOINTMENT_END)));
//                appointments.add(appointmentsAddModel);
//            }
//            return appointments;
//        } catch (SQLException e) {
//            System.out.println("conflictingAppointmentsQuery failed " + e.getMessage());
//        }
//        return null;
//    }

    public void insertAppointment(AppointmentsAddModel appointment) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start," +
                        " end, createDate, createdBy, lastUpdateBy) VALUES(?,?,?,?,?,?,?,?,?,?, now(),?,?)")) {
            preparedStatement.setInt(1, appointment.getCustomerId());
            preparedStatement.setInt(2, appointment.getUserId());
            preparedStatement.setString(3, "unknown");
            preparedStatement.setString(4, "unknown");
            preparedStatement.setString(5, "unknown");
            preparedStatement.setString(6, "unknown");
            preparedStatement.setString(7, appointment.getType());
            preparedStatement.setString(8, "unknown");
            preparedStatement.setTimestamp(9, Timestamp.valueOf(convertToUTC(appointment.getStart())));
            preparedStatement.setTimestamp(10, Timestamp.valueOf(convertToUTC(appointment.getEnd())));
            preparedStatement.setString(11, "Admin");
            preparedStatement.setString(12, "Admin");
            preparedStatement.execute();



        } catch (SQLException e) {
            System.out.println("insertAppointment failed " + e.getMessage());
        }
    }

    public void updateAppointment(AppointmentsUpdateModel appointment) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE appointment SET customerId = ?, userId = ?, type = ?, start = ?, end = ?, lastUpdateBy = ? " +
                        "WHERE appointmentId = ?")) {
            preparedStatement.setInt(1, appointment.getCustomerId());
            preparedStatement.setInt(2, appointment.getUserId());
            preparedStatement.setString(3, appointment.getType());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(appointment.getLocalDateTimeStart()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appointment.getLocalDateTimeEnd()));
            preparedStatement.setString(6, "Admin");
            preparedStatement.setInt(7, appointment.getAppointmentId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("updateAppointment failed " + e.getMessage());
        }
    }

    public int insertCountry(CustomerRecordsAddModel country) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO country(country, createDate, createdBy, lastUpdateBy) VALUES(?,now(),?,?)")) {
            preparedStatement.setString(1, country.getCountry());
            preparedStatement.setString(2, "Admin");
            preparedStatement.setString(3, "Admin");
            preparedStatement.execute();
            try (PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT LAST_INSERT_ID() FROM country");
                    ResultSet resultSet = preparedStatement2.executeQuery()){
                resultSet.next();
                int countryId = resultSet.getInt("countryId");
                return countryId;
            }
        } catch (SQLException e) {
            System.out.println("insertCountry failed " + e.getMessage());
        }
        return -1;
    }

    public int insertCity(CustomerRecordsAddModel city, int countryId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO city(city, countryId, createDate, createdBy, lastUpdateBy) VALUES(?,?, now(),?,?)")){
            preparedStatement.setString(1, city.getCity());
            preparedStatement.setInt(2, countryId);
            preparedStatement.setString(3, "Admin");
            preparedStatement.setString(4, "Admin");
            preparedStatement.execute();
            try (PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT LAST_INSERT_ID() FROM city");
                    ResultSet resultSet = preparedStatement2.executeQuery()){
                resultSet.next();
                int cityId = resultSet.getInt("cityId");
                return cityId;
            }
        } catch (SQLException e) {
            System.out.println("insertCity failed " + e.getMessage());
        }
        return -1;
    }

    public int insertAddress(CustomerRecordsAddModel address, int cityId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy) "
                   + "VALUES(?,?,?,?,?, now(),?,?)")) {
            preparedStatement.setString(1, address.getAddress());
            preparedStatement.setString(2, "");
            preparedStatement.setInt(3, cityId);
            preparedStatement.setString(4, "12345");
            preparedStatement.setString(5, address.getPhone());
            preparedStatement.setString(6, "Admin");
            preparedStatement.setString(7, "Admin");
            preparedStatement.execute();
            try (PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT LAST_INSERT_ID() FROM address");
                    ResultSet resultSet = preparedStatement2.executeQuery()){
                resultSet.next();
                int addressId = resultSet.getInt("addressId");
                return addressId;
            }
        } catch (SQLException e) {
            System.out.println("insertAddress failed " + e.getMessage());
        }
        return -1;
    }

    public void insertCustomer(CustomerRecordsAddModel customer, int addressId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy) " +
                        "VALUES(?,?,?, now(),?,?)")){
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setInt(2, addressId);
            preparedStatement.setInt(3, 1);
            preparedStatement.setString(4, "Admin");
            preparedStatement.setString(5, "Admin");
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("insertCustomer failed " + e.getMessage());
        }
    }

    public void updateAddress(CustomerRecordsUpdateModel address) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE address SET address = ?, phone = ? WHERE addressId = ?")){
            preparedStatement.setString(1, address.getAddress());
            preparedStatement.setString(2, address.getPhone());
            preparedStatement.setInt(3, address.getAddressId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("updateAddress failed " + e.getMessage());
        }
    }

    public void updateCustomer(CustomerRecordsUpdateModel customerRecordsUpdateModel) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE customer SET customerName = ? WHERE customerId = ?")){
            preparedStatement.setString(1, customerRecordsUpdateModel.getCustomerName());
            preparedStatement.setInt(2, customerRecordsUpdateModel.getAddressId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("updateCustomer failed " + e.getMessage());
        }
    }






//**********************************************************************************************************************

    public ArrayList<User> queryUser() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_USER);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            ArrayList<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(constructUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            System.out.println("Database Query on user failed " + e.getMessage());
        }
        return null;
    }

    public User constructUser(ResultSet resultSet) {
        try {
            User user = new User(resultSet.getInt(COLUMN_USER_USER_ID),
                    resultSet.getString(COLUMN_USER_USER_NAME),
                    resultSet.getString(COLUMN_USER_PASSWORD));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Customer> queryCustomer() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT customer.customerId, customer.customerName, address.addressId, address.address, address.phone, " +
                   "city.cityId, city.city, country.countryId, country.country " +
                   "FROM customer " +
                   "INNER JOIN address ON customer.addressId  = address.addressId " +
                   "INNER JOIN city ON address.cityId = city.cityId " +
                   "INNER JOIN country ON city.countryId = country.countryId ");
                ResultSet resultSet = preparedStatement.executeQuery()) {
            ArrayList<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(constructCustomer(resultSet));
            }
            return customers;
        } catch (SQLException e) {
            System.out.println("queryCustomer failed " + e.getMessage());
        }
        return null;
    }

    public Customer constructCustomer(ResultSet resultSet) {
        try {
            Country country = new Country(resultSet.getInt(COLUMN_COUNTRY_COUNTRY_ID),
                    resultSet.getString(COLUMN_COUNTRY_COUNTRY));
            City city = new City(resultSet.getInt(COLUMN_CITY_CITY_ID),
                    resultSet.getString(COLUMN_CITY_CITY), country);
            Address address = new Address(resultSet.getInt(COLUMN_ADDRESS_ADDRESS_ID),
                    resultSet.getString(COLUMN_ADDRESS_ADDRESS),
                    resultSet.getString(COLUMN_ADDRESS_PHONE), city);
            Customer customer = new Customer(resultSet.getInt(COLUMN_CUSTOMER_CUSTOMER_ID),
                    resultSet.getString(COLUMN_CUSTOMER_CUSTOMER_NAME), address);
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Appointment>  queryAppointment() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT appointment.appointmentId, appointment.type, appointment.start, appointment.end, " +
                "user.userId, user.userName, user.password, " +
                "customer.customerId, customer.customerName," +
                "address.addressId, address.address, address.phone," +
                "city.cityId, city.city, " +
                "country.countryId, country.country " +
                "FROM appointment " +
                "INNER JOIN user ON appointment.userId = user.userId " +
                "INNER JOIN customer ON appointment.customerId = customer.customerId " +
                "INNER JOIN address ON customer.addressId  = address.addressId " +
                "INNER JOIN city ON address.cityId = city.cityId " +
                "INNER JOIN country ON city.countryId = country.countryId");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            ArrayList<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                Customer customer = constructCustomer(resultSet);
                User user = constructUser(resultSet);
                Appointment appointment = constructAppointment(resultSet, customer, user);
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            System.out.println("Database query on appointments failed " + e.getStackTrace());
        }
        return null;
    }

    public Appointment constructAppointment(ResultSet resultSet, Customer customer, User user) {
        try {
            Appointment appointment = new Appointment(resultSet.getInt(COLUMN_APPOINTMENT_APPOINTMENT_ID),
                    resultSet.getString(COLUMN_APPOINTMENT_TYPE),
                    convertToZonedLocalDateTime(resultSet.getTimestamp(COLUMN_APPOINTMENT_START)),
                    convertToZonedLocalDateTime(resultSet.getTimestamp(COLUMN_APPOINTMENT_END)),
                    customer, user);
            return appointment;
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    public LocalDateTime convertToZonedLocalDateTime(Timestamp timestamp) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId.of("UTC")).withZoneSameInstant(zoneId);
        LocalDateTime zonedLocalDateTime = zonedDateTime.toLocalDateTime();
        return zonedLocalDateTime;
    }

    public LocalDateTime convertToUTC(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId).withZoneSameInstant(zoneId.of("UTC"));
        return zonedDateTime.toLocalDateTime();
    }


    public ArrayList<ReportByAppointmentTypeModel> numberAppointmentTypesByMonthQuery() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT MONTHNAME(start), COUNT(DISTINCT type) FROM appointment GROUP BY MONTHNAME(start)");
                ResultSet resultSet = preparedStatement.executeQuery()) {
            ArrayList<ReportByAppointmentTypeModel> reports = new ArrayList<>();
            while (resultSet.next()) {
                ReportByAppointmentTypeModel reportByAppointmentTypeModel = new ReportByAppointmentTypeModel();
                reportByAppointmentTypeModel.setMonth(resultSet.getString("MONTHNAME(start)"));
                reportByAppointmentTypeModel.setCount(resultSet.getInt("COUNT(DISTINCT type)"));
                reports.add(reportByAppointmentTypeModel);
            }
            return reports;
        } catch (SQLException e) {
            System.out.println("numberAppointmentTypesByMonthQuery failed " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Appointment> scheduleForEachConsultantQuery(String userName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT appointment.appointmentId, appointment.type, appointment.start, appointment.end, " +
                        "user.userId, user.userName, user.password, " +
                        "customer.customerId, customer.customerName," +
                        "address.addressId, address.address, address.phone," +
                        "city.cityId, city.city, " +
                        "country.countryId, country.country " +
                        "FROM U04ltA.appointment " +
                        "INNER JOIN U04ltA.user ON appointment.userId = user.userId " +
                        "INNER JOIN U04ltA.customer ON appointment.customerId = customer.customerId " +
                        "INNER JOIN address ON customer.addressId  = address.addressId " +
                        "INNER JOIN city ON address.cityId = city.cityId " +
                        "INNER JOIN country ON city.countryId = country.countryId" +
                        "WHERE user.userName = ?")) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                Customer customer = constructCustomer(resultSet);
                User user = constructUser(resultSet);
                Appointment appointment = constructAppointment(resultSet, customer, user);
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            System.out.println("scheduleForEachConsultantQuery failed " + e.getMessage());
        }
        return null;
    }

    public ArrayList<ReportTotalAppointmentsByConsultantModel> appointmentsForEachConsultantQuery() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT user.userName, COUNT(appointment.appointmentId) "
                        + "FROM appointment, user "
                        + "WHERE appointment.userId = user.userId "
                        + "GROUP BY user.userName");
                ResultSet resultSet = preparedStatement.executeQuery()) {
            ArrayList<ReportTotalAppointmentsByConsultantModel> appointments = new ArrayList<>();
            while (resultSet.next()) {
                ReportTotalAppointmentsByConsultantModel reportTotalAppointmentsByConsultnatModel = new ReportTotalAppointmentsByConsultantModel();
                reportTotalAppointmentsByConsultnatModel.setUserName(resultSet.getString("userName"));
                reportTotalAppointmentsByConsultnatModel.setCount(resultSet.getInt("COUNT(appointment.appointmentId)"));
                appointments.add(reportTotalAppointmentsByConsultnatModel);
            }
            return appointments;
        } catch (SQLException e) {
            System.out.println("appointmentsForEachConsultantQuery failed" + e.getMessage());
        }
        return null;
    }




}
