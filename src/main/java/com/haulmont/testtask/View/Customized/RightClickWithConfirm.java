package com.haulmont.testtask.View.Customized;

import com.vaadin.event.ContextClickEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.UI;
import org.vaadin.dialogs.ConfirmDialog;

/**
 * Created by Inferno on 21.01.2017.
 */
public class RightClickWithConfirm {
    AbstractComponent component;
    private ConfirmedAction confirmedAction;
    String textConfirm;

    public RightClickWithConfirm(AbstractComponent componentListener, ConfirmedAction confirmedAction, String textConfirm) {
        this.component = componentListener;
        this.confirmedAction = confirmedAction;

        this.textConfirm = textConfirm;
        component.addContextClickListener((ContextClickEvent.ContextClickListener) event -> clearWithConfirm());
    }

    private void clearWithConfirm() {
        ConfirmDialog.show(UI.getCurrent(), textConfirm,
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {
                        confirmedAction.doConfirmed();
                    }
                });
    }
}
