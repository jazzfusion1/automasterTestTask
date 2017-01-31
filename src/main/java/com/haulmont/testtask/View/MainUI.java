package com.haulmont.testtask.View;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.viritin.layouts.MVerticalLayout;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        MVerticalLayout layout = new MVerticalLayout()
                .withMargin(true);
        layout.setSizeFull();
        layout.addComponent(new ViewImpl());
        setContent(layout);
    }
}