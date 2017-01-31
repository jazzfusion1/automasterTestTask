package com.haulmont.testtask.View.toolbars;

import com.haulmont.testtask.View.Customized.ConfirmedAction;
import com.haulmont.testtask.View.Customized.RightClickWithConfirm;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.vaadin.viritin.button.MButton;


/**
 * Created by Inferno on 18.01.2017.
 */
public class LookupTool extends CustomField implements ConfirmedAction {
    private Class typeClass;
    private LookupSupport lookupSupport;
    private TextField textField = new TextField();

    public LookupTool(Class typeClass, String caption, LookupSupport alookupSupport) {
        this.typeClass = typeClass;
        this.lookupSupport = alookupSupport;
        new RightClickWithConfirm(this, this, "Очистить значение поля?");
        setCaption(caption);
    }

    public void setTextFieldWidth(String width) {
        this.textField.setWidth(width);
    }

    @Override
    public void setConvertedValue(Object convertedValue) {
        textField.setConvertedValue(convertedValue == null ? "" : convertedValue.toString());
        super.setConvertedValue(convertedValue);
    }


    protected Component initContent() {
        HorizontalLayout layout = new HorizontalLayout();
        Button selButton = new Button("...", event -> lookupSupport.openLookup(this));
        MButton delButton = new MButton("", clickEvent -> doConfirmed())
                .withIcon(FontAwesome.ERASER)
                .withDescription("очистить");
        textField.setEnabled(false);
        Object value = getPropertyDataSource() == null ? null : getPropertyDataSource().getValue();
        textField.setConvertedValue(value == null ? "" : value.toString());
        layout.addComponent(textField);
        layout.addComponent(selButton);
        layout.addComponent(delButton);
        return layout;
    }


    @Override
    public Class getType() {
        return typeClass;
    }


    @Override
    public void doConfirmed() {
        this.setConvertedValue(null);
    }
}