package ru.dbolonkin.vaadin.ui;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import ru.dbolonkin.vaadin.database.CustomerDAO;
import ru.dbolonkin.vaadin.entity.Customer;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */

@Theme("mytheme")
public class MyUI extends UI {
    private CustomerDAO customerDAO = new CustomerDAO();
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private CustomerForm form = new CustomerForm(this);


    public void updateList() throws IOException, SQLException, ClassNotFoundException {
        List<Customer> customers = customerDAO.findAll();
        grid.setItems(customers);
    }
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        form.setVisible(false);

        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);

        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });

        HorizontalLayout toolbar = new HorizontalLayout(addCustomerBtn);

        layout.addComponents(toolbar, main);

        try {
            updateList();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException  e) {
            e.printStackTrace();
        }


        setContent(layout);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                form.setVisible(false);
            } else {
                form.setCustomer(event.getValue());
            }
        });
    }

    @WebServlet(urlPatterns = {"/ui/*", "/VAADIN/*"}, name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
