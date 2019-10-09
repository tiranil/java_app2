package ru.dbolonkin.vaadin;

import org.junit.*;
import ru.dbolonkin.vaadin.database.CustomerDAO;
import ru.dbolonkin.vaadin.database.DatabaseCon;
import ru.dbolonkin.vaadin.entity.Customer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerTest {
    private Customer customer;
    private Customer customer1;
    private Customer customer2;
    private CustomerDAO customerDAO = new CustomerDAO();

    @BeforeClass
    public static void createTable() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = DatabaseCon.getConn();
        Statement statement = connection.createStatement();
        boolean st = statement.execute("CREATE TABLE test_customers (id SERIAL UNIQUE,first_name TEXT,last_name TEXT);");
        boolean st1 = statement.execute("INSERT INTO test_customers VALUES (DEFAULT, 'Denis', 'Bolonkin');");
        boolean st2 = statement.execute("INSERT INTO test_customers VALUES (DEFAULT, 'Maksim', 'Kopitin');");
        boolean st3 = statement.execute("INSERT INTO test_customers VALUES (DEFAULT, 'Vladimir', 'Popov');");
    }

    @AfterClass
    public static void deleteTable() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = DatabaseCon.getConn();
        Statement statement = connection.createStatement();
        boolean st = statement.execute("DROP TABLE test_customers;");
    }

    @Before
    public void setCustomers() throws Exception {
        customer = new Customer(1L, "Denis", "Bolonkin");
        customer1 = new Customer(2L, "Maksim", "Kopitin");
        customer2 = new Customer(3L, "Vladimir", "Popov");
    }

    @Test
    public void getAllUsers() throws SQLException, IOException, ClassNotFoundException {

        List<Customer> actual = new ArrayList<>();
        actual.add(customer);
        actual.add(customer1);
        actual.add(customer2);

        List<Customer> expected = customerDAO.findAll("test_customers");

        for (Customer customer : actual) {
            Assert.assertEquals(expected.iterator().next().getId(), expected.iterator().next().getId());
            Assert.assertEquals(expected.iterator().next().getFirstName(), expected.iterator().next().getFirstName());
            Assert.assertEquals(expected.iterator().next().getLastName(), expected.iterator().next().getLastName());
        }
    }

    @Test
    public void getUserById() throws SQLException, IOException, ClassNotFoundException {

        Customer actual = customer1;
        Customer expected = customerDAO.findEntityById("test_customers", 2);

        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getFirstName(), expected.getFirstName());
        Assert.assertEquals(actual.getLastName(), expected.getLastName());
    }

    @Test
    public void deleteUserById() throws SQLException, IOException, ClassNotFoundException {
        Customer actual = customerDAO.findEntityById("test_customers", 2);
        customerDAO.delete("test_customers", actual);
        Assert.assertNull(customerDAO.findEntityById("test_customers", 2));
    }

    @Test
    public void updateUser() throws SQLException, IOException, ClassNotFoundException {
        Customer actual = customer;
        actual.setFirstName("Dmitrii");
        Customer expected = customerDAO.findEntityById("test_customers", 1);
        expected.setFirstName("Dmitrii");
        customerDAO.update("test_customers", expected);
        Customer expected2 = customerDAO.findEntityById("test_customers", 1);
        Assert.assertEquals(actual.getFirstName(), expected2.getFirstName());
    }

    @Test
    public void createUser() throws SQLException, IOException, ClassNotFoundException {
        Customer actual = new Customer(4L, "Oleg", "Zhukov");
        customerDAO.create("test_customers", new Customer(4L, "Oleg", "Zhukov"));
        Customer expected = customerDAO.findEntityById("test_customers", 4);
        Assert.assertEquals(actual.getFirstName(), expected.getFirstName());
    }
}
