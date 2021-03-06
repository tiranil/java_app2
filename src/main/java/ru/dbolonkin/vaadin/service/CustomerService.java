package ru.dbolonkin.vaadin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.dbolonkin.vaadin.database.CustomerDAO;
import ru.dbolonkin.vaadin.entity.Customer;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.*;


@Path("/customers")
public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();

    public CustomerService() throws PropertyVetoException, IOException, SQLException {
    }


    @GET
    @Produces("application/json")
    public String getAll() throws IOException, SQLException, ClassNotFoundException, PropertyVetoException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        List<Customer> customers = customerDAO.findAll("customers");
        String list = new String();
        for (Customer customer : customers) {
            list += objectMapper.writeValueAsString(customer);
        }
        return list;
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public String getOne(@PathParam("id") int id) throws IOException, SQLException, ClassNotFoundException, PropertyVetoException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String list = new String();
        Customer customer = customerDAO.findEntityById("customers", id);
        if (customer != null) {
            list = list.concat(objectMapper.writeValueAsString(customer));
        } else {
            list = list.concat("There is no customer with such id. Please try to input another id.");
        }
        return list;
    }


    @POST
    @Consumes("application/json")
    public void create(String s) throws IOException, SQLException, ClassNotFoundException, PropertyVetoException {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer customer = objectMapper.readValue(s, Customer.class);
        customerDAO.create("customers", customer);
    }


    @PUT
    @Consumes("application/json")
    public void update(String s) throws IOException, SQLException, ClassNotFoundException, PropertyVetoException {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer customer = objectMapper.readValue(s, Customer.class);
        customerDAO.update("customers", customer);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) throws IOException, SQLException, ClassNotFoundException, PropertyVetoException {
        Customer customer = customerDAO.findEntityById("customers", id);
        if (customer != null) {
            customerDAO.delete("customers", customer);
        } else {
            System.out.println("There is no customer with such id. Please try to input another id.");
        }
    }


}




