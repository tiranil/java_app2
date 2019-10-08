package ru.dbolonkin.vaadin.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import ru.dbolonkin.vaadin.entity.Customer;

import java.io.IOException;

public class CustomerDeserializer extends StdDeserializer<Customer> {

    public CustomerDeserializer() {
        this(null);
    }


    public CustomerDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Customer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        long id = (Integer) ((IntNode) node.get("id")).numberValue();
        String firstName = node.get("firstName").asText();
        String lastName = node.get("lastName").asText();


        return new Customer(id, firstName, lastName);
    }
}
