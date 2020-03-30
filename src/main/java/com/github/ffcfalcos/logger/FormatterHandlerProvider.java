package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.util.FileService;
import com.github.ffcfalcos.logger.util.XmlReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Provides Formatter Handlers
 */
@SuppressWarnings("unused")
public class FormatterHandlerProvider implements HandlerProvider<FormatterHandler> {

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
        File file = FileService.getConfigFile();
        if(file != null) {
            try {
                for(String formatterHandlerClassName : XmlReader.getElements(file, "formatter-handler")) {
                    Class<?> formatterHandlerClass = Class.forName(formatterHandlerClassName);
                    add((FormatterHandler) formatterHandlerClass.getConstructor().newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    public void add(FormatterHandler formatterHandler) {
        if (formatterHandler != null) {
            this.formatterHandlers.add(formatterHandler);
        }
    }
}
