package ru.dbolonkin.vaadin;

import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class CustomerForm extends com.vaadin.ui.FormLayout{
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<Customer> binder = new Binder<>(Customer.class);

    private CustomerDAO customerDAO = new CustomerDAO();
    private Customer customer;
    private MyUI myUI;

    public CustomerForm(MyUI myUI) {
        this.myUI = myUI;
        binder.bindInstanceFields(this);
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        addComponents(firstName, lastName, buttons);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
    }

    public void setCustomer(Customer customer) {


            this.customer = customer;        final boolean persisted = customer.getId() != null;

        binder.setBean(customer);


        // Show delete button for only customers already in the database
        delete.setVisible(persisted);
        setVisible(true);
        firstName.selectAll();
    }

    private void delete() {
        customerDAO.delete(customer);
        myUI.updateList();
        setVisible(false);
    }

    private void save() {

        final boolean persisted = customer.getId() != null;
        if (persisted) {
            customerDAO.update(customer);
        }
        else {
            customerDAO.create(customer);
        }
        myUI.updateList();
        setVisible(false);
    }
}
