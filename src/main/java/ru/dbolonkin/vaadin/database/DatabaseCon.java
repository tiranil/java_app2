package ru.dbolonkin.vaadin.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseCon {

    public DatabaseCon() throws FileNotFoundException {
    }

    public static Connection getConn() throws IOException, ClassNotFoundException, SQLException {
        FileInputStream fis = new FileInputStream("connection.prop");
        Properties p = new Properties();
        p.load(fis);
        String driver = (String) p.get("driver");
        String url = (String) p.get("URL");
        String username = (String) p.get("user");
        String password = (String) p.get("password");
        Connection connection = null;
        System.out.println("Testing connection to PostgreSQL JDBC");

        Class.forName(driver);

        System.out.println("PostgreSQL JDBC Driver successfully connected");

        connection = DriverManager.getConnection(url, username, password);

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
        return connection;
    }

    public static void main(String[] argv) {

    }
}

