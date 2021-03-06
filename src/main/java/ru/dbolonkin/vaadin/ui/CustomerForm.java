package ru.dbolonkin.vaadin.ui;

import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import ru.dbolonkin.vaadin.database.CustomerDAO;
import ru.dbolonkin.vaadin.entity.Customer;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

public class CustomerForm extends com.vaadin.ui.FormLayout {
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<Customer> binder = new Binder<>(Customer.class);

    private CustomerDAO customerDAO = new CustomerDAO();
    private Customer customer;
    private MyUI myUI;

    public CustomerForm(MyUI myUI) throws PropertyVetoException, IOException, SQLException {
        this.myUI = myUI;
        binder.bindInstanceFields(this);
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        addComponents(firstName, lastName, buttons);
        save.addClickListener(e -> {

            try {
                this.save();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
            }

        });
        delete.addClickListener(e -> {
            try {
                this.delete();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
        final boolean persisted = customer.getId() != null;
        binder.setBean(customer);

        delete.setVisible(persisted);
        setVisible(true);
        firstName.selectAll();
    }

    private void delete() throws IOException, SQLException, ClassNotFoundException, PropertyVetoException {
        customerDAO.delete("customers", customer);
        myUI.updateList();
        setVisible(false);
    }

    private void save() throws IOException, SQLException, ClassNotFoundException, PropertyVetoException {

        final boolean persisted = customer.getId() != null;
        if (persisted) {
            customerDAO.update("customers", customer);
        } else {
            customerDAO.create("customers", customer);
        }
        myUI.updateList();
        setVisible(false);
    }
}
