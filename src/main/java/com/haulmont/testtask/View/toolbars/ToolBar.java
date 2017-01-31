package com.haulmont.testtask.View.toolbars;

import com.haulmont.testtask.View.AbstractScreen;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;

/**
 * Created by Inferno on 07.01.2017.
 */
public class ToolBar extends CustomComponent{
    public ToolBar(AbstractScreen aabstractScreen){
     setCompositionRoot( defineToolBar(aabstractScreen));
    }

    private Component defineToolBar(AbstractScreen abstractScreen) {
        HorizontalLayout layout = new HorizontalLayout();
        Button add = new MButton("Добавить", event -> abstractScreen.addClick());
//        setClickShortcut работает весьма странно
//        add.setClickShortcut(ShortcutAction.KeyCode.N);
        add.setIcon(FontAwesome.PLUS_SQUARE);
        Button edit = new MButton("Измеить",event ->  abstractScreen.editClick());
        edit.setIcon( FontAwesome.PENCIL_SQUARE_O);

//        edit.setClickShortcut(ShortcutAction.KeyCode.E);
        Button del = new ConfirmButton("Удалить","Уалить?",
                event -> abstractScreen.delClick());
//        del.setClickShortcut(ShortcutAction.KeyCode.DELETE);
        del.setIcon(FontAwesome.TRASH_O);
        layout.addComponent(add);
        layout.addComponent(edit);
        layout.addComponent(del);

        return layout;
    }
}
