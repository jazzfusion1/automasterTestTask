package com.haulmont.testtask.View.filters;

import com.haulmont.testtask.DAO.entity.CustomerEntity;
import com.haulmont.testtask.DAO.entity.SearchOrdersParams;
import com.haulmont.testtask.View.CustomersScreen;
import com.haulmont.testtask.View.Customized.EnumFillComboBox;
import com.haulmont.testtask.View.OrdersScreen;
import com.haulmont.testtask.View.toolbars.LookupTool;
import com.haulmont.testtask.enumerations.OrderStatus;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MPanel;

/**
 * Created by Inferno on 20.01.2017.
 */
public class OrderFilterPanel extends CustomComponent {


    public OrderFilterPanel() {
        setCompositionRoot(defineContent());
    }

    private BeanFieldGroup<SearchOrdersParams> fieldGroup = new BeanFieldGroup<>(SearchOrdersParams.class);

    private Component defineContent() {
        final MPanel filter = new MPanel("Фильтр")
                .withIcon(FontAwesome.GLASS);

        LookupTool lookupTool = new LookupTool(CustomerEntity.class, "Клиент", CustomersScreen.getInstance());
        lookupTool.setTextFieldWidth("500");
        EnumFillComboBox comboBox = new EnumFillComboBox("Статус заказа", OrderStatus.class);
        comboBox.setNullSelectionAllowed(true);
        MTextField textField = new MTextField("Описание")
                .withInputPrompt("Часть описания")
                .withWidth("350");
        Button apply = new Button("Применить", this::okClick);
        fieldGroup.setItemDataSource(new SearchOrdersParams());

        MHorizontalLayout layout = new MHorizontalLayout()
                .withDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        layout.setSpacing(true);
        layout.addComponents(lookupTool, comboBox, textField, apply);
        fieldGroup.bind(lookupTool, "customer");
        fieldGroup.bind(comboBox, "status");
        fieldGroup.bind(textField, "descPart");
        filter.setContent(layout);

        return filter;
    }

    private void okClick(Button.ClickEvent event) {
        try {
            fieldGroup.commit();
            OrdersScreen.getInstance()
                    .setSearchOrdersParams((SearchOrdersParams) fieldGroup.getItemDataSource().getBean());
        } catch (FieldGroup.CommitException e) {
            e.printStackTrace();
        }
    }
}
