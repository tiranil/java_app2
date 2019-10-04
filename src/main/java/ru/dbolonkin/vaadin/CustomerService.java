package ru.dbolonkin.vaadin;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/service")
public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();

    @Path("/getAll")
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


}




