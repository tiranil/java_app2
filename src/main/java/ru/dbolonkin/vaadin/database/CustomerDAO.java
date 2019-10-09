package ru.dbolonkin.vaadin.database;

import ru.dbolonkin.vaadin.entity.Customer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends AbstractDAO<Integer, Customer> {




    @Override
    public List<Customer> findAll(String table) throws IOException, SQLException, ClassNotFoundException {
        String SQL_SELECT_ALL_USERS = String.format("SELECT * FROM %s",table);
        List<Customer> customers = new ArrayList<>();
        Connection connection = DatabaseCon.getConn();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_USERS);
        while (rs.next()) {
            long id = rs.getInt(1);
            String firstName = rs.getString(2);
            String lastName = rs.getString(3);
            customers.add(new Customer(id, firstName, lastName));
        }
        return customers;
    }

    @Override
    public Customer findEntityById(String table, Integer id) throws SQLException, IOException, ClassNotFoundException {
        String SQL_SELECT_USER_ID = String.format("SELECT * FROM %s WHERE id=?",table);
        Customer customer = null;
        Connection connection = DatabaseCon.getConn();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_ID);
        statement.setLong(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            String firstName = rs.getString(2);
            String lastName = rs.getString(3);
            customer = new Customer(id.longValue(), firstName, lastName);
        }
        return customer;
    }

    @Override
    public boolean delete(String table, Customer entity) throws SQLException, IOException, ClassNotFoundException {
        String SQL_DELETE_USER = String.format("DELETE FROM %s WHERE id=?",table);
        Connection connection = DatabaseCon.getConn();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER);
        statement.setLong(1, entity.getId());
        boolean ex = statement.execute();
        System.out.println("User deleted");
        return true;
    }

    @Override
    public boolean create(String table, Customer entity) throws SQLException, IOException, ClassNotFoundException {
        String SQL_CREATE_USER = String.format("INSERT INTO %s VALUES (DEFAULT,?,?)",table);
        Connection connection = DatabaseCon.getConn();
        PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER);
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        boolean ex = statement.execute();
        System.out.println("User created");
        return true;
    }


    @Override
    public Customer update(String table, Customer entity) throws IOException, SQLException, ClassNotFoundException {
        String SQL_UPDATE_USER = String.format("UPDATE %s SET first_name = ?, last_name = ? WHERE id=?",table);
        Customer customer = entity;
        Connection connection = DatabaseCon.getConn();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER);
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        statement.setLong(3, entity.getId());
        int rs = statement.executeUpdate();
        if (rs != 0) {
            System.out.println("User updated.");
        } else {
            System.out.println("There is no user with such id. Please input another id.");
        }
        return customer;
    }

}

