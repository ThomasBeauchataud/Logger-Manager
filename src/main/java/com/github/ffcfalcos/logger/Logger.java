package com.github.ffcfalcos.logger;

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

    private HandlerProvider<FormatterHandler> formatterHandlerProvider;
    private HandlerProvider<PersistingHandler> persistingHandlerProvider;
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
     * {@inheritDoc}
     */
    @Override
    public HandlerProvider<FormatterHandler> getFormatterHandlerProvider() {
        return formatterHandlerProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerProvider<PersistingHandler> getPersistingHandlerProvider() {
        return persistingHandlerProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoggerStatisticsManagement getLoggerStatisticsManagement() {
        return loggerStatisticsManagement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(LogContent content) {
        log(content, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(Object content, Severity severity) {
        log(content, severity, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(LogContent content, Class<?> persistingHandlerClass, Class<?> formatterHandlerClass) {
        PersistingHandler persistingHandler = persistingHandlerProvider.get(persistingHandlerClass);
        FormatterHandler formatterHandler = formatterHandlerProvider.get(formatterHandlerClass);
        persistingHandler.persist(formatterHandler.format(content));
        loggerStatisticsManagement.update(persistingHandler.getClass(), formatterHandler.getClass());
    }

    /**
     * {@inheritDoc}
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
     *
     * @return LoggerInterface
     */
    public static LoggerInterface getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
}
