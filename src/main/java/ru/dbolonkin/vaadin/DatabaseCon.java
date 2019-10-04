package ru.dbolonkin.vaadin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseCon {

    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://localhost/test";
    static final String USER = "postgres";
    static final String PASS = "masterpass";


    public static Connection getConn() {
        Connection connection = null;
        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");


        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
        return connection;
    }

    public static void main(String[] argv) throws SQLException {

        CustomerDAO customerDAO = new CustomerDAO();
        System.out.println(customerDAO.findAll());
        System.out.println(customerDAO.findEntityById(1));
        System.out.println(customerDAO.getNextId());


    }
}

