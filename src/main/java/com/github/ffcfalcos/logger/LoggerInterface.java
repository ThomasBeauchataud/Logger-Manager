package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.collector.Severity;
import com.github.ffcfalcos.logger.handler.formatter.FormatterHandler;
import com.github.ffcfalcos.logger.handler.persisting.PersistingHandler;
import com.github.ffcfalcos.logger.rule.storage.RuleStorageHandler;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused","rawtypes"})
public interface LoggerInterface {

    void addPersistingHandler(PersistingHandler persistingHandler);

    void addPersistingHandlers(List<PersistingHandler> persistingHandlers);

    PersistingHandler getPersistingHandler(Class PersistingHandlerClass);

    List<PersistingHandler> getPersistingHandlers();

    void setDefaultPersistingHandler(Class persistingHandlerClass);

    void addFormatterHandler(FormatterHandler formatterHandler);

    void addFormatterHandlers(List<FormatterHandler> formatterHandlers);

    FormatterHandler getFormatterHandler(Class formatterHandlerClass);

    void setDefaultFormatterHandler(Class formatterHandlerClass);

    void log(Map<String, Object> message);

    void log(Map<String, Object> message, Class persistingHandlerClass, Class formatterHandlerClass);

    void log(String message, Severity severity);

    void log(String message, Severity severity, Class persistingHandlerClass, Class formatterHandlerClass);

    RuleStorageHandler getRuleStorageHandler();

    void setRuleStorageHandler(RuleStorageHandler ruleStorageHandler);

}
