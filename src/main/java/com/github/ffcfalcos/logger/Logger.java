package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.handler.formatter.StringFormatterHandler;
import com.github.ffcfalcos.logger.handler.formatter.FormatterHandlerInterface;
import com.github.ffcfalcos.logger.handler.formatter.JsonFormatterHandler;
import com.github.ffcfalcos.logger.handler.persisting.FilePersistingHandler;
import com.github.ffcfalcos.logger.handler.persisting.PersistingHandlerInterface;
import com.github.ffcfalcos.logger.handler.persisting.RabbitMQPersistingHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Default
@ApplicationScoped
public class Logger implements LoggerInterface {

    private PersistingHandlerInterface defaultPersistingHandler = loadDefaultPersistingHandler();
    private FormatterHandlerInterface defaultFormatterHandler = loadDefaultFormatterHandler();
    private List<PersistingHandlerInterface> persistingHandlerList = loadPersistingHandlers(defaultPersistingHandler);
    private List<FormatterHandlerInterface> formatterHandlerList = loadFormatterHandlers(defaultFormatterHandler);

    @Override
    public void addPersistingHandler(PersistingHandlerInterface persistingHandler) {
        persistingHandlerList.add(persistingHandler);
    }

    @Override
    public void addPersistingHandlers(List<PersistingHandlerInterface> persistingHandlers) {
        for(PersistingHandlerInterface persistingHandler : persistingHandlers) {
            addPersistingHandler(persistingHandler);
        }
    }

    @Override
    public void addFormatterHandler(FormatterHandlerInterface formatterHandler) {
        formatterHandlerList.add(formatterHandler);
    }

    @Override
    public void addFormatterHandlers(List<FormatterHandlerInterface> formatterHandlers) {
        for(FormatterHandlerInterface formatterHandler : formatterHandlers) {
            addFormatterHandler(formatterHandler);
        }
    }

    @Override
    public FormatterHandlerInterface getFormatterHandler(String formatterHandlerName) {
        for(FormatterHandlerInterface formatterHandler : this.formatterHandlerList) {
            if(formatterHandler.getClass().getSimpleName().equals(formatterHandlerName)) {
                return formatterHandler;
            }
        }
        return defaultFormatterHandler;
    }

    @Override
    public PersistingHandlerInterface getPersistingHandler(String persistingHandlerName) {
        for(PersistingHandlerInterface persistingHandler : persistingHandlerList) {
            if(persistingHandler.getClass().getSimpleName().equals(persistingHandlerName)) {
                return persistingHandler;
            }
        }
        return defaultPersistingHandler;
    }

    @Override
    public void log(Map<String, Object> message) {
        log(message, defaultPersistingHandler.getClass().getSimpleName(), defaultFormatterHandler.getClass().getSimpleName());
    }

    @Override
    public void log(Map<String, Object> message, String persistingHandlerName) {
        log(message, persistingHandlerName, defaultFormatterHandler.getClass().getSimpleName());
    }

    @Override
    public void log(Map<String, Object> message, String persistingHandlerName, String formatterHandlerName) {
        String finalContent = getFormatterHandler(formatterHandlerName).format(message);
        getPersistingHandler(persistingHandlerName).persist(finalContent);
    }

    @Override
    public void log(String content) {
        log(content, defaultPersistingHandler.getClass().getSimpleName(), defaultFormatterHandler.getClass().getSimpleName());
    }

    @Override
    public void log(String message, String persistingHandlerName) {
        log(message, persistingHandlerName, defaultPersistingHandler.getClass().getSimpleName());
    }

    @Override
    public void log(String message, String persistingHandlerName, String formatterHandlerName) {
        String finalContent = getFormatterHandler(formatterHandlerName).format(message);
        getPersistingHandler(persistingHandlerName).persist(finalContent);
    }

    private PersistingHandlerInterface loadDefaultPersistingHandler() {
        return new RabbitMQPersistingHandler();
    }

    private FormatterHandlerInterface loadDefaultFormatterHandler() { return new JsonFormatterHandler(); }

    private List<PersistingHandlerInterface> loadPersistingHandlers(PersistingHandlerInterface defaultPersistingHandler) {
        List<PersistingHandlerInterface> list = new ArrayList<>();
        list.add(defaultPersistingHandler);
        list.add(new FilePersistingHandler());
        return list;
    }

    private List<FormatterHandlerInterface> loadFormatterHandlers(FormatterHandlerInterface defaultFormatterHandler) {
        List<FormatterHandlerInterface> list = new ArrayList<>();
        list.add(defaultFormatterHandler);
        list.add(new StringFormatterHandler());
        return list;
    }

}
