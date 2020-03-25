package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.collector.LogContent;
import com.github.ffcfalcos.logger.collector.Severity;
import com.github.ffcfalcos.logger.handler.formatter.FormatterHandlerProvider;
import com.github.ffcfalcos.logger.handler.formatter.FormatterHandler;
import com.github.ffcfalcos.logger.handler.persisting.*;
import com.github.ffcfalcos.logger.statistic.LoggerStatisticsManagement;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Logger component accessible by singleton or dependency injection
 */
@Default
@ApplicationScoped
@SuppressWarnings("Duplicates")
public class Logger implements LoggerInterface {

    private static LoggerInterface instance;

    private FormatterHandlerProvider formatterHandlerProvider;
    private PersistingHandlerProvider persistingHandlerProvider;
    private LoggerStatisticsManagement loggerStatisticsManagement;

    /**
     * Logger Constructor
     */
    public Logger() {
        formatterHandlerProvider = new FormatterHandlerProvider();
        persistingHandlerProvider = new PersistingHandlerProvider();
        loggerStatisticsManagement = new LoggerStatisticsManagement();
    }

    /**
     * Copy the dependency injection instance in the singleton instance
     */
    @PostConstruct
    public void initialize() {
        instance = this;
    }

    /**
     * Return the PersistingHandlerProvider to manage PersistingHandlers
     * @return PersistingHandlerProvider
     */
    @Override
    public FormatterHandlerProvider getFormatterHandlerProvider() {
        return formatterHandlerProvider;
    }

    /**
     * Return the FormatterHandlerProvider to manage FormatterHandlers
     * @return FormatterHandlerProvider
     */
    @Override
    public PersistingHandlerProvider getPersistingHandlerProvider() {
        return persistingHandlerProvider;
    }

    /**
     * Return the LoggerStatisticsManagement to manage it
     * @return LoggerStatisticsManagement
     */
    @Override
    public LoggerStatisticsManagement getLoggerStatisticsManagement() {
        return loggerStatisticsManagement;
    }

    /**
     * Log a LogContent object with the defaults FormatterHandler and PersistingHandler
     * @param content LogContent
     */
    @Override
    public void log(LogContent content) {
        log(content, null, null);
    }

    /**
     * Log a string object with the default FormatterHandler and the default PersistingHandler
     * @param content Object
     * @param severity Severity
     */
    @Override
    public void log(Object content, Severity severity) {
        log(content, severity, null, null);
    }

    /**
     * Log a LogContent object with specifics FormatterHandler and PersistingHandler
     * @param persistingHandlerClass Class | null to use the default PersistingHandler
     * @param formatterHandlerClass Class | null to use the default FormatterHandler
     * @param content LogContent
     */
    @Override
    public void log(LogContent content, Class<?> persistingHandlerClass, Class<?> formatterHandlerClass) {
        PersistingHandler persistingHandler = persistingHandlerProvider.get(persistingHandlerClass);
        FormatterHandler formatterHandler = formatterHandlerProvider.get(formatterHandlerClass);
        persistingHandler.persist(formatterHandler.format(content));
        loggerStatisticsManagement.update(persistingHandler.getClass(), formatterHandler.getClass());
    }

    /**
     * Log a string object with specifics FormatterHandler and PersistingHandler
     * @param persistingHandlerClass Class | null to use the default PersistingHandler
     * @param formatterHandlerClass Class | null to use the default FormatterHandler
     * @param content Object
     * @param severity Severity
     */
    @Override
    public void log(Object content, Severity severity, Class<?> persistingHandlerClass, Class<?> formatterHandlerClass) {
        PersistingHandler persistingHandler = persistingHandlerProvider.get(persistingHandlerClass);
        FormatterHandler formatterHandler = formatterHandlerProvider.get(formatterHandlerClass);
        persistingHandler.persist(formatterHandler.format(content, severity));
        loggerStatisticsManagement.update(persistingHandler.getClass(), formatterHandler.getClass());
    }

    /**
     * Return the singleton instance
     * @return LoggerInterface
     */
    public static LoggerInterface getInstance() {
        if(instance == null) {
            instance = new Logger();
        }
        return instance;
    }

}
