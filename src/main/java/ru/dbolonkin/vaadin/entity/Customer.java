package ru.dbolonkin.vaadin.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.dbolonkin.vaadin.service.CustomerDeserializer;

import javax.persistence.*;

@JsonDeserialize(using = CustomerDeserializer.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_generator")
    @SequenceGenerator(name = "customers_generator", sequenceName = "customers_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String firstName;
    private String lastName;

    public Long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Customer(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer() { }



}
