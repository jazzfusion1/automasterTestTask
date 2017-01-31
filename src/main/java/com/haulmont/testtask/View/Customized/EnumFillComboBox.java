package com.haulmont.testtask.View.Customized;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;

import java.util.EnumSet;

/**
 * Created by Inferno on 20.01.2017.
 */
public class EnumFillComboBox extends ComboBox implements ConfirmedAction {
    public EnumFillComboBox(String caption, Class enumClass) {
        super(caption);
        new RightClickWithConfirm(this, this, "Очистить значение поля?");
        final BeanItemContainer<? extends Enum> statusContainer = new BeanItemContainer<>(enumClass);
        statusContainer.addAll(EnumSet.allOf(enumClass));
        setNullSelectionAllowed(false);
        setContainerDataSource(statusContainer);
    }

    @Override
    public void doConfirmed() {
        setConvertedValue(null);
    }
}
