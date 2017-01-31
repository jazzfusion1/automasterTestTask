package com.haulmont.testtask.View;

import com.haulmont.testtask.DAO.entity.CustomerEntity;
import com.haulmont.testtask.View.toolbars.LookupSupport;
import com.haulmont.testtask.View.toolbars.OkCancelBar;
import com.haulmont.testtask.View.toolbars.OkCancelBarListener;
import com.haulmont.testtask.View.toolbars.ToolBar;
import com.haulmont.testtask.controller.Controller;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by Inferno on 07.01.2017.
 */
public class CustomersScreen extends AbstractScreen implements LookupSupport {

    private static CustomersScreen vaadinScreen = null;
    private ParametrizedTable parametrizedTable = getParametrizedTable();

    private CustomersScreen() {
        setCaption("Клиенты");
        setCompositionRoot(defineContent());
        updateTableData();
    }


    private Component defineContent() {
        VerticalLayout layout = new VerticalLayout();
        parametrizedTable.setCaption("Список клиентов");
        parametrizedTable.setPageLength(12);
        layout.addComponent(parametrizedTable);
        layout.addComponent(new ToolBar(this));
        return layout;
    }

    void updateTableData() {
        updateTableData(null);
    }

    void updateTableData(Table table) {
        Table customersTable = table == null ? parametrizedTable : table;
        List<CustomerEntity> customers = Controller.getInstance().findAllCustomers();
        BeanItemContainer<CustomerEntity> container = new BeanItemContainer<>(
                CustomerEntity.class, customers);
        customersTable.setContainerDataSource(container);
        customersTable.setVisibleColumns("name", "surname", "secname", "phone");
        customersTable.setColumnHeaders("Имя", "Фамилия", "Отчество", "Телефон");
        for (Object o : customersTable.getVisibleColumns()) {
            customersTable.setColumnExpandRatio(o,0.25f);
        }
    }


    public static CustomersScreen getInstance() {
        if (vaadinScreen == null) {
            vaadinScreen = new CustomersScreen();
        }
        return vaadinScreen;
    }


    private CustomerEntity getSelectedEntity() {
        return (CustomerEntity) parametrizedTable.getValue();
    }

    @Override
    public void addClick() {
        UI.getCurrent().addWindow(new CustomerWindow(new CustomerEntity()));

    }

    @Override
    public void editClick() {
        CustomerEntity customerEntity = (CustomerEntity) parametrizedTable.getValue();
        if (customerEntity != null) {
            UI.getCurrent().addWindow(new CustomerWindow(customerEntity));
        } else {
            nothingToSelect();
        }
    }

    @Override
    public void delClick() {
        CustomerEntity customerEntity = getSelectedEntity();
        if (customerEntity != null) {
            if (!Controller.getInstance().deleteCustomer(customerEntity)) {
                Notification.show("У данного клиента имеются заказы");
            }
        } else {
            nothingToSelect();
        }
    }

    class CustomerWindow extends ConfiguredWindow implements OkCancelBarListener {
        CustomerWindow(CustomerEntity customerEntity) {
            initWindow(customerEntity);
        }

        private void initWindow(CustomerEntity customer) {
            binder.setItemDataSource(customer);
            VerticalLayout verticalLayout = new VerticalLayout();
            FormLayout formLayout = new FormLayout();
            formLayout.setCaption("Редактирование сведений о клиенте");

            RegexpValidator personDataValidator = new RegexpValidator("([A-zА-я-])*",
                    "Некорректный ввод");


            final TextField name = new TextField("Имя");
            name.setNullRepresentation("");
            name.addValidator(personDataValidator);
            name.addValidator(new StringLengthValidator("Имя слишком длинное",
                    0, 100, true));
//            name.addTextChangeListener(event -> validateRealtime(name, event));
            name.setRequired(true);
            binder.bind(name, "name");
            name.setIcon(FontAwesome.USER);
            formLayout.addComponent(name);

            final TextField surname = new TextField("Фамилия", "surname");
            surname.setNullRepresentation("");
            surname.setRequired(true);
            surname.addValidator(personDataValidator);
            surname.addValidator(new StringLengthValidator("Фамилия слишком длинная",
                    0, 100, true));
//            surname.addTextChangeListener(event -> validateRealtime(surname, event));
            binder.bind(surname, "surname");
            formLayout.addComponent(surname);

            final TextField secname = new TextField("Отчество", "secname");
            secname.setNullRepresentation("");
            secname.addValidator(personDataValidator);
            secname.addValidator(new StringLengthValidator("Отчество слишком длинное",
                    0, 100, true));
//            secname.addTextChangeListener(event -> validateRealtime(secname, event));
            binder.bind(secname, "secname");
            formLayout.addComponent(secname);

            final TextField phone = new TextField("Телефон");
            phone.setInputPrompt("+");
            phone.setNullRepresentation("");
            phone.addValidator(new RegexpValidator("[+]?[0-9]*", "Not a number"));
            phone.addTextChangeListener(event -> validateRealtime(phone, event));
            phone.setValidationVisible(true);
            phone.setImmediate(true);
            phone.setIcon(FontAwesome.PHONE);
            phone.setRequired(true);
            binder.bind(phone, "phone");
            formLayout.addComponent(phone);

            formLayout.addComponent(new OkCancelBar(this));
            formLayout.setWidthUndefined();
            verticalLayout.addComponent(formLayout);
            verticalLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);
            verticalLayout.setMargin(true);
            setContent(verticalLayout);
        }

        @Override
        public void okClick() {

            try {
                binder.commit();
                Controller.getInstance().saveOrUpdate(binder.getItemDataSource().getBean());
                close();
            } catch (FieldGroup.CommitException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void cancelClick() {
            binder.discard();
            close();
        }

        final BeanFieldGroup<CustomerEntity> binder =
                new BeanFieldGroup<>(CustomerEntity.class);
    }
}