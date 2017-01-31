package com.haulmont.testtask.View.toolbars;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by Inferno on 07.01.2017.
 */
public class OkCancelBar extends CustomComponent {
    public OkCancelBar(OkCancelBarListener window) {
        setCompositionRoot(defineOkCancelBar(window));
    }

    private Component defineOkCancelBar(OkCancelBarListener window) {
        HorizontalLayout layout = new HorizontalLayout();
        Button cancel = new Button("Отменить", event -> window.cancelClick());
        cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        Button ok = new Button("OK", event -> window.okClick());
        ok.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        layout.addComponent(ok);
        layout.addComponent(cancel);
        return layout;
    }
}
