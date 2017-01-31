package com.haulmont.testtask.View;

import com.haulmont.testtask.Model.Model;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import java.util.Arrays;
import java.util.List;

public class ViewImpl extends CustomComponent implements Model.ModelChangeListnener, View {


    ViewImpl() {
        Model.getInstance().addChangeListener(this);
        VerticalLayout layout = new VerticalLayout();
        Component tabSheet = defineTabSheet(CustomersScreen.getInstance(), OrdersScreen.getInstance());
        layout.addComponent(tabSheet);
        setCompositionRoot(layout);
    }


    private Component defineTabSheet(Component... components) {
        List<Component> componentList = Arrays.asList(components);
        TabSheet tabSheet = new TabSheet();
        componentList.forEach(tabSheet::addTab);
        return tabSheet;
    }

    @Override
    public void modelChanged() {
        CustomersScreen.getInstance().updateTableData();
        OrdersScreen.getInstance().updateTableData();

    }
}

