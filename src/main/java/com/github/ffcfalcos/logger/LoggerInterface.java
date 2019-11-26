package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.handler.formatter.FormatterHandlerInterface;
import com.github.ffcfalcos.logger.handler.persisting.PersistingHandlerInterface;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public interface LoggerInterface {

    void addPersistingHandler(PersistingHandlerInterface persistingHandler);

    void addPersistingHandlers(List<PersistingHandlerInterface> persistingHandlers);

    PersistingHandlerInterface getPersistingHandler(String persistingHandlerName);

    void setDefaultPersistingHandler(String persistingHandlerName);

    void addFormatterHandler(FormatterHandlerInterface formatterHandler);

    void addFormatterHandlers(List<FormatterHandlerInterface> formatterHandlers);

    FormatterHandlerInterface getFormatterHandler(String formatterHandlerName);

    void setDefaultFormatterHandler(String formatterHandlerName);

    void log(Map<String, Object> message);

    void log(Map<String, Object> message, String persistingHandlerName);

    void log(Map<String, Object> message, String persistingHandlerName, String formatterHandlerName);

    void log(String message);

    void log(String message, String persistingHandlerName);

    void log(String message, String persistingHandlerName, String formatterHandlerName);

}
