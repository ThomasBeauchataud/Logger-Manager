package com.github.ffcfalcos.logger.handler.formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Provide Formatter Handlers
 */
@SuppressWarnings("unused")
public class FormatterHandlerProvider {

    private FormatterHandler defaultFormatterHandler;
    private List<FormatterHandler> formatterHandlers;

    /**
     * FormatterHandlerProvider Constructor
     */
    public FormatterHandlerProvider() {
        defaultFormatterHandler = new JsonFormatterHandler();
        formatterHandlers = new ArrayList<>();
        formatterHandlers.add(defaultFormatterHandler);
        formatterHandlers.add(new StringFormatterHandler());
    }

    /**
     * Return a FormatterHandler associated to the class in parameter
     * Return the default FormatterHandler if the one in parameter doesn't exists
     *
     * @param formatterHandlerClass Class | null to get the default FormatterHandler
     * @return FormatterHandler
     */
    public FormatterHandler get(Class<?> formatterHandlerClass) {
        if (formatterHandlerClass == null) {
            return defaultFormatterHandler;
        }
        for (FormatterHandler formatterHandler : this.formatterHandlers) {
            if (formatterHandler.getClass().equals(formatterHandlerClass)) {
                return formatterHandler;
            }
        }
        return defaultFormatterHandler;
    }

    /**
     * Change the default FormatterHandler
     *
     * @param formatterHandlerClass Class
     */
    public void setDefault(Class<?> formatterHandlerClass) {
        if (formatterHandlerClass != null) {
            for (FormatterHandler formatterHandler : this.formatterHandlers) {
                if (formatterHandler.getClass().equals(formatterHandlerClass)) {
                    defaultFormatterHandler = formatterHandler;
                }
            }
        }
    }

    /**
     * Add a new FormatterHandler to the provider
     *
     * @param formatterHandler FormatterHandler
     */
    public void add(FormatterHandler formatterHandler) {
        if (formatterHandler != null) {
            this.formatterHandlers.add(formatterHandler);
        }
    }
}
