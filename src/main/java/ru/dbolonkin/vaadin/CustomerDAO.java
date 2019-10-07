package ru.dbolonkin.vaadin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends AbstractDAO<Integer, Customer> {
    public static final String SQL_SELECT_ALL_USERS = "SELECT * FROM customer";
    public static final String SQL_SELECT_USER_ID = "SELECT * FROM customer WHERE id=?";
    public static final String SQL_DELETE_USER = "DELETE FROM customer WHERE id=?";
    public static final String SQL_UPDATE_USER = "UPDATE customer SET first_name = ?, last_name = ? WHERE id=?";
    public static final String SQL_CREATE_USER = "INSERT INTO customer VALUES (?,?,?)";
    public static final String SQL_GET_ID = "SELECT MAX(id) FROM customer;";




    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseCon.getConn();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (rs.next()) {
                long id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                customers.add(new Customer(id, firstName,lastName));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    @Override
    public Customer findEntityById(Integer id) {
       Customer customer = null;
        try (Connection connection = DatabaseCon.getConn();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_USER_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                customer = new Customer(id.longValue(), firstName, lastName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean delete(Customer entity) {
        try (Connection connection = DatabaseCon.getConn();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_DELETE_USER)) {
            statement.setLong(1, entity.getId());
            ResultSet rs = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("User deleted");
        return true;
        }

    public long getNextId() {
        Long nextId = null;
        try (Connection connection = DatabaseCon.getConn();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_GET_ID)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nextId = rs.getLong(1);
                return nextId+1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }
    @Override
    public boolean create(Customer entity) {

        Long nextId = getNextId();
        try (Connection connection = DatabaseCon.getConn();
        PreparedStatement statement =
                     connection.prepareStatement(SQL_CREATE_USER)) {
            statement.setLong(1, nextId );
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            ResultSet rs = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println("User created");
        return true;
    }



    @Override
    public Customer update(Customer entity) {
        Customer customer = entity;
        try (Connection connection = DatabaseCon.getConn();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_UPDATE_USER)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setLong(3, entity.getId());
            int rs = statement.executeUpdate();
            if (rs != 0) {
                System.out.println("User updated.");
            }
            else {
                System.out.println("There is no user with such id. Please input another id.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return customer;
    }
    }

