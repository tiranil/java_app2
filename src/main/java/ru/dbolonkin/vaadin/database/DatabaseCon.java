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


    public static Connection getConn() throws IOException {
        FileInputStream fis=new FileInputStream("connection.prop");
        Properties p=new Properties ();
        p.load (fis);
        String driver= (String) p.get ("driver");
        String url= (String) p.get ("URL");
        String username= (String) p.get ("user");
        String password= (String) p.get ("password");
        Connection connection = null;
        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");


        try {
            connection = DriverManager
                    .getConnection(url,username,password);

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

    public static void main(String[] argv)  {



    }
}

