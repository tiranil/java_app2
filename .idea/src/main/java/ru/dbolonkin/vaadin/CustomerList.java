package ru.dbolonkin.vaadin;

import java.util.List;

public class CustomerList {

    private List<Customer> customers;

    public void setList(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getList() {
        return customers;
    }
}
