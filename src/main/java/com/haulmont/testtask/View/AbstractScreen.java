package com.haulmont.testtask.View;

import com.haulmont.testtask.View.toolbars.LookupTool;
import com.haulmont.testtask.View.toolbars.OkCancelBar;
import com.haulmont.testtask.View.toolbars.OkCancelBarListener;
import com.haulmont.testtask.View.toolbars.ToolBarListener;
import com.vaadin.data.Validator;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;
import org.vaadin.viritin.layouts.MVerticalLayout;


/**
 * Created by Inferno on 07.01.2017.
 */
public abstract class AbstractScreen extends CustomComponent implements ToolBarListener {
    abstract void updateTableData(Table table);

    private ParametrizedTable parametrizedTable = new ParametrizedTable();


    ParametrizedTable getParametrizedTable() {
        return parametrizedTable;
    }

    public void openLookup(LookupTool lookupTool) {
        LookupWindow window = new LookupWindow(lookupTool);
        UI.getCurrent().addWindow(window);
        this.updateTableData(window.getParametrizedTable());
    }


    void validateRealtime(AbstractTextField field, FieldEvents.TextChangeEvent event)
            throws Validator.InvalidValueException {
            field.setValue(event.getText());
            field.setCursorPosition(event.getCursorPosition());
            field.validate();
    }


    class LookupWindow extends ConfiguredWindow implements OkCancelBarListener {

        LookupWindow(LookupTool alookupTool) {
            setCaption("Произведите выбор");
            setContent(initContent());
            setSizeFull();
            this.lookupTool = alookupTool;
        }

        LookupTool lookupTool;
        private ParametrizedTable parametrizedTable = new ParametrizedTable();

        private Component initContent() {
            parametrizedTable.addItemClickListener(this::tableClick);
            VerticalLayout layout = new VerticalLayout();
            layout.addComponent(parametrizedTable);
            layout.addComponent(new OkCancelBar(this));
            layout.setMargin(true);
            return layout;
        }

        private void tableClick(ItemClickEvent itemClickEvent) {
            if (itemClickEvent.isDoubleClick()){
                okClick();
            }
        }


        ParametrizedTable getParametrizedTable() {
            return parametrizedTable;
        }


        @Override
        public void okClick() {

            lookupTool.setConvertedValue(parametrizedTable.getValue());
            if (lookupTool.getConvertedValue() == null) {
                nothingToSelect();
            } else {
                close();
            }
        }

        @Override
        public void cancelClick() {
            close();
        }
    }

    void nothingToSelect() {
        Notification.show("Ничего не выбрано", Notification.Type.WARNING_MESSAGE);
    }
}

class ParametrizedTable extends Table {


    private MVerticalLayout layout = new MVerticalLayout();

    ParametrizedTable() {
       super();
       this.setNullSelectionAllowed(false);
       this.setSelectable(true);
       this.setImmediate(true);
       this.setPageLength(10);

       this.setSizeFull();
    }


}

