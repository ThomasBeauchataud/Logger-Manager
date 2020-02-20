package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.collector.Severity;
import com.github.ffcfalcos.logger.handler.formatter.StringFormatterHandler;
import com.github.ffcfalcos.logger.handler.formatter.FormatterHandler;
import com.github.ffcfalcos.logger.handler.formatter.JsonFormatterHandler;
import com.github.ffcfalcos.logger.handler.persisting.ConsolePersistingHandler;
import com.github.ffcfalcos.logger.handler.persisting.FilePersistingHandler;
import com.github.ffcfalcos.logger.handler.persisting.PersistingHandler;
import com.github.ffcfalcos.logger.handler.persisting.RabbitMQPersistingHandler;
import com.github.ffcfalcos.logger.rule.storage.CsvRuleStorageHandler;
import com.github.ffcfalcos.logger.rule.storage.RuleStorageHandler;
import com.github.ffcfalcos.logger.statistic.LoggerStatisticsManagement;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Default
@ApplicationScoped
@SuppressWarnings({"Duplicates","rawtypes"})
public class Logger implements LoggerInterface {

    private static LoggerInterface instance;

    private PersistingHandler defaultPersistingHandler;
    private FormatterHandler defaultFormatterHandler;
    private List<PersistingHandler> persistingHandlerList;
    private List<FormatterHandler> formatterHandlerList;
    private RuleStorageHandler ruleStorageHandler;
    private LoggerStatisticsManagement loggerStatisticsManagement;

    public Logger() {
        defaultPersistingHandler = new RabbitMQPersistingHandler();
        defaultFormatterHandler = new JsonFormatterHandler();
        persistingHandlerList = new ArrayList<>();
        persistingHandlerList.add(defaultPersistingHandler);
        persistingHandlerList.add(new FilePersistingHandler());
        persistingHandlerList.add(new ConsolePersistingHandler());
        formatterHandlerList = new ArrayList<>();
        formatterHandlerList.add(defaultFormatterHandler);
        formatterHandlerList.add(new StringFormatterHandler());
        ruleStorageHandler = new CsvRuleStorageHandler();
        loggerStatisticsManagement = new LoggerStatisticsManagement();
    }

    @PostConstruct
    public void initialize() {
        instance = this;
    }

    @Override
    public void addPersistingHandler(PersistingHandler persistingHandler) {
        persistingHandlerList.add(persistingHandler);
    }

    @Override
    public void addPersistingHandlers(List<PersistingHandler> persistingHandlers) {
        for(PersistingHandler persistingHandler : persistingHandlers) {
            addPersistingHandler(persistingHandler);
        }
    }

    @Override
    public void addFormatterHandler(FormatterHandler formatterHandler) {
        formatterHandlerList.add(formatterHandler);
    }

    @Override
    public void addFormatterHandlers(List<FormatterHandler> formatterHandlers) {
        for(FormatterHandler formatterHandler : formatterHandlers) {
            addFormatterHandler(formatterHandler);
        }
    }

    @Override
    public FormatterHandler getFormatterHandler(Class formatterHandlerClass) {
        if(formatterHandlerClass == null) { return defaultFormatterHandler; }
        for(FormatterHandler formatterHandler : this.formatterHandlerList) {
            if(formatterHandler.getClass().equals(formatterHandlerClass)) {
                return formatterHandler;
            }
        }
        return defaultFormatterHandler;
    }

    @Override
    public void setDefaultFormatterHandler(Class formatterHandlerClass) {
        defaultFormatterHandler = getFormatterHandler(formatterHandlerClass);
    }

    @Override
    public PersistingHandler getPersistingHandler(Class persistingHandlerClass) {
        if(persistingHandlerClass == null) { return defaultPersistingHandler; }
        for(PersistingHandler persistingHandler : persistingHandlerList) {
            if(persistingHandler.getClass().equals(persistingHandlerClass)) {
                return persistingHandler;
            }
        }
        return defaultPersistingHandler;
    }

    @Override
    public List<PersistingHandler> getPersistingHandlers() {
        return persistingHandlerList;
    }

    @Override
    public void setDefaultPersistingHandler(Class persistingHandlerClass) {
        defaultPersistingHandler = this.getPersistingHandler(persistingHandlerClass);
    }

    @Override
    public void log(Map<String, Object> message) {
        log(message, null, null);
    }

    @Override
    public void log(String content, Severity severity) {
        log(content, severity, null, null);
    }

    @Override
    public void log(Map<String, Object> message, Class persistingHandlerClass, Class formatterHandlerClass) {
        PersistingHandler persistingHandler = getPersistingHandler(persistingHandlerClass);
        FormatterHandler formatterHandler = getFormatterHandler(formatterHandlerClass);
        persistingHandler.persist(formatterHandler.format(message));
        loggerStatisticsManagement.update(persistingHandlerClass, formatterHandlerClass);
    }

    @Override
    public void log(String message, Severity severity, Class persistingHandlerClass, Class formatterHandlerClass) {
        PersistingHandler persistingHandler = getPersistingHandler(persistingHandlerClass);
        FormatterHandler formatterHandler = getFormatterHandler(formatterHandlerClass);
        persistingHandler.persist(formatterHandler.format(message, severity));
        loggerStatisticsManagement.update(persistingHandlerClass, formatterHandlerClass);
    }

    @Override
    public RuleStorageHandler getRuleStorageHandler() {
        return ruleStorageHandler;
    }

    @Override
    public void setRuleStorageHandler(RuleStorageHandler ruleStorageHandler) {
        this.ruleStorageHandler = ruleStorageHandler;
    }

    public static LoggerInterface getInstance() {
        if(instance == null) {
            instance = new Logger();
        }
        return instance;
    }

}
