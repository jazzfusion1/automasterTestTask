package com.haulmont.testtask.View;


import com.haulmont.testtask.DAO.entity.CustomerEntity;
import com.haulmont.testtask.DAO.entity.OrderEntity;
import com.haulmont.testtask.DAO.entity.SearchOrdersParams;
import com.haulmont.testtask.View.Customized.EnumFillComboBox;
import com.haulmont.testtask.View.converters.CustomStringToDateConverter;
import com.haulmont.testtask.View.filters.OrderFilterPanel;
import com.haulmont.testtask.View.toolbars.LookupTool;
import com.haulmont.testtask.View.toolbars.OkCancelBar;
import com.haulmont.testtask.View.toolbars.OkCancelBarListener;
import com.haulmont.testtask.View.toolbars.ToolBar;
import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.enumerations.OrderStatus;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBigDecimalConverter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;
import org.vaadin.addons.maskedtextfield.NumericField;
import org.vaadin.viritin.MBeanFieldGroup;
import org.vaadin.viritin.fields.MDateField;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Inferno on 10.01.2017.
 */
public class OrdersScreen extends AbstractScreen {

    private static OrdersScreen vaadinScreen = null;

    private ParametrizedTable parametrizedTable = getParametrizedTable();

    private SearchOrdersParams getSearchOrdersParams() {
        return searchOrdersParams;
    }

    private OrdersScreen() {
        setCaption("Заказы");
        setCompositionRoot(defineContent());
        updateTableData();
    }

    public void setSearchOrdersParams(SearchOrdersParams searchOrdersParams) {
        this.searchOrdersParams = searchOrdersParams;
        updateTableData();
    }

    private SearchOrdersParams searchOrdersParams;

    private Component defineContent() {
        VerticalLayout layout = new VerticalLayout();
        parametrizedTable.setCaption("Список заказов");
        layout.addComponent(new OrderFilterPanel());
        layout.addComponent(parametrizedTable);
        layout.addComponent(new ToolBar(this));
        layout.setSizeFull();
        return layout;
    }

    void updateTableData() {
        updateTableData(null);
    }

    void updateTableData(Table table) {
        Table ordersTable = table == null ? parametrizedTable : table;
        List<OrderEntity> orders = Controller.getInstance().findOrdersByParams(getSearchOrdersParams());
        BeanItemContainer<OrderEntity> container = new BeanItemContainer<>(
                OrderEntity.class, orders);
        ordersTable.setContainerDataSource(container);
        ordersTable.setVisibleColumns("desc", "customer", "createDate", "endWorkDate", "cost", "status");
        ordersTable.setColumnHeaders("Описание", "Клиент", "Дата создания", "Дата окончания работ", "Стоимость", "Статус");
        ordersTable.setColumnExpandRatio("desc",0.3f);
        ordersTable.setColumnExpandRatio("customer",0.3f);

        ordersTable.setConverter("createDate", new CustomStringToDateConverter());
        ordersTable.setConverter("endWorkDate", new CustomStringToDateConverter());
    }


    public static OrdersScreen getInstance() {
        if (vaadinScreen == null) {
            vaadinScreen = new OrdersScreen();
        }
        return vaadinScreen;
    }


    @Override
    public void addClick() {
        UI.getCurrent().addWindow(new OrderWindow(new OrderEntity()));
    }

    @Override
    public void editClick() {
        OrderEntity orderEntity = (OrderEntity) parametrizedTable.getValue();
        if (orderEntity != null) {
            UI.getCurrent().addWindow(new OrderWindow(orderEntity));
        } else {
            nothingToSelect();
        }

    }

    @Override
    public void delClick() {
        if (parametrizedTable.getValue() == null){
            nothingToSelect();
        } else {
            Controller.getInstance().deleteOrder((OrderEntity) parametrizedTable.getValue());
        }
    }

    class OrderWindow extends ConfiguredWindow implements OkCancelBarListener {
        OrderWindow(OrderEntity orderEntity) {
            initWindow(orderEntity);
        }

        private void initWindow(OrderEntity order) {
            binder.setItemDataSource(order);
            binder.addValidator(MValidator -> {

                if (MValidator.getEndWorkDate() != null & MValidator.getCreateDate() != null)
                    if (MValidator.getEndWorkDate().getTime() < MValidator.getCreateDate().getTime()) {
                        throw new Validator.InvalidValueException(
                                "Дата создания не может быть больше даты завершения заказа");
                    }
            });
            binder.setValidateAllProperties(true);

            VerticalLayout verticalLayout = new VerticalLayout();
            FormLayout formLayout = new FormLayout();
            formLayout.setCaption("Редактирование сведений о заказе");

            final TextArea desc = new TextArea("Описание");
            desc.setNullRepresentation("");
            desc.addValidator(new StringLengthValidator("Описание слишком длинное",
                    0, 254, true));
            //desc.addTextChangeListener(event -> validateRealtime(desc, event));
            desc.setRequired(true);
            binder.bind(desc, "desc");
            formLayout.addComponent(desc);

            final LookupTool customer = new LookupTool(CustomerEntity.class, "Клиент",
                    CustomersScreen.getInstance());
            customer.setRequired(true);
            binder.bind(customer, "customer");
            formLayout.addComponent(customer);

            final MDateField createDate = new MDateField("Дата создания");
            createDate.setImmediate(true);
            binder.bind(createDate, "createDate");
            formLayout.addComponent(createDate);

            final MDateField endWorkDate = new MDateField("Дата окончания работ");
            endWorkDate.setImmediate(true);
            binder.bind(endWorkDate, "endWorkDate");
            formLayout.addComponent(endWorkDate);

            final NumericField cost = new NumericField("Стоимость");
            cost.setNullRepresentation("");
            cost.setConverter(new StringToBigDecimalConverter());
            cost.addValidator(new BigDecimalRangeValidator("Стоимость не может быть отрицательной",
                    BigDecimal.ZERO, BigDecimal.valueOf(Double.MAX_VALUE)));
            binder.bind(cost, "cost");
            cost.setValidationVisible(true);
            cost.setImmediate(true);
            formLayout.addComponent(cost);

            ComboBox status = new EnumFillComboBox("Статус заказа", OrderStatus.class);
            status.setRequired(true);
            binder.bind(status, "status");
            formLayout.addComponent(status);

            formLayout.addComponent(new OkCancelBar(this));
            formLayout.setWidthUndefined();
            verticalLayout.addComponent(formLayout);
            verticalLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);
            verticalLayout.setMargin(true);
            setContent(verticalLayout);
        }

        @Override
        public void okClick() {


            if (binder.isValid()) {
                Controller.getInstance().saveOrUpdate(binder.getItemDataSource().getBean());
                close();
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                binder.getBeanLevelValidationErrors().forEach(s -> stringBuilder.append("\r\n").append(s));
                Notification.show("Ошибка ввода "+stringBuilder.toString());
            }
        }

        @Override
        public void cancelClick() {
            close();
        }

        final MBeanFieldGroup<OrderEntity> binder =
                new MBeanFieldGroup<>(OrderEntity.class)
                        .withEagerValidation();
    }

}
