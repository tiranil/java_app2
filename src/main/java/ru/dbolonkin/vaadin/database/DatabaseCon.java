package ru.dbolonkin.vaadin.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseCon {

    private static ComboPooledDataSource cpds;

    static {
        try {
            cpds = getDataSource();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ComboPooledDataSource getDataSource() throws PropertyVetoException, IOException {
        FileInputStream fis = new FileInputStream("connection.prop");
        Properties p = new Properties();
        p.load(fis);
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl((String) p.get("URL"));
        cpds.setUser((String) p.get("user"));
        cpds.setPassword((String) p.get("password"));
        cpds.setDriverClass((String) p.get("driver"));

        // Optional Settings
        cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(100);

        return cpds;
    }

    public static Connection getConn() throws IOException, SQLException, PropertyVetoException {
        Connection connection;
        connection = cpds.getConnection();
        return connection;
    }

    public static void main(String[] argv) {

    }
}

