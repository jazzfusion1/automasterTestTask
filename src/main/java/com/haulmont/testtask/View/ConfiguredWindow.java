package com.haulmont.testtask.View;

import com.vaadin.ui.Window;

/**
 * Created by Inferno on 27.12.2016.
 */
public abstract class ConfiguredWindow extends Window {

    ConfiguredWindow() {
        center();
        setClosable(false);
        setModal(true);
        setResizable(false);
    }
}
