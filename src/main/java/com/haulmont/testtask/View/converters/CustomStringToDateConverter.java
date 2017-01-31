package com.haulmont.testtask.View.converters;

import com.vaadin.data.util.converter.StringToDateConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Inferno on 20.01.2017.
 */
public class CustomStringToDateConverter extends StringToDateConverter {
    @Override

    public DateFormat getFormat(Locale locale) {

        return new SimpleDateFormat("yyyy-MM-dd E");
    }
}
