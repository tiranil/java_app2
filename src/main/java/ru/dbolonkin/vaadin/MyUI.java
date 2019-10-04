package ru.dbolonkin.vaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;

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


    public void updateList() {
        List<Customer> customers = customerDAO.findAll();
        grid.setItems(customers);
    }
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        form.setVisible(false);

        // add Grid to the layout
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

        // fetch list of Customers from service and assign it to Grid
        updateList();


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
