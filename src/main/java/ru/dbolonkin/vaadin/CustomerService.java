package ru.dbolonkin.vaadin;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/customers")
public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();


    @GET
    @Produces("application/json")
    public String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        List<Customer> customers = customerDAO.findAll();
        String list = new String();
        for (Customer customer : customers) {
            list += objectMapper.writeValueAsString(customer);
        }
        return  list;
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public String getOne(@PathParam("id") int id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String list = new String();
        Customer customer = customerDAO.findEntityById(id);
        if (customer != null) {
            list = list.concat(objectMapper.writeValueAsString(customer));
        }
        else {
            list = list.concat("There is no customer with such id. Please try to input another id.");
        }
        return list;
        }




    @POST
    @Consumes("application/json")
    public void create(String s) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer customer = objectMapper.readValue(s, Customer.class);
        customerDAO.create(customer);
    }



    @PUT
    @Consumes("application/json")
    public void update(String s) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer customer = objectMapper.readValue(s, Customer.class);
        customerDAO.update(customer);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) {
        Customer customer = customerDAO.findEntityById(id);
        if(customer != null) {
            customerDAO.delete(customer);
        }
        else {
            System.out.println("There is no customer with such id. Please try to input another id.");
        }
    }


}




