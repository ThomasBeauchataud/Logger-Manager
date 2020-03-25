package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.collector.LogContent;
import com.github.ffcfalcos.logger.collector.Severity;
import com.github.ffcfalcos.logger.statistic.LoggerStatisticsManagement;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Logger component accessible by singleton or dependency injection
 */
@SuppressWarnings("unused")
public interface LoggerInterface {

    /**
     * Return the PersistingHandlerProvider to manage PersistingHandlers
     * @return PersistingHandlerProvider
     */
    PersistingHandlerProvider getPersistingHandlerProvider();

    /**
     * Return the FormatterHandlerProvider to manage FormatterHandlers
     * @return FormatterHandlerProvider
     */
    FormatterHandlerProvider getFormatterHandlerProvider();

    /**
     * Return the LoggerStatisticsManagement to manage it
     * @return LoggerStatisticsManagement
     */
    LoggerStatisticsManagement getLoggerStatisticsManagement();

    /**
     * Log a LogContent object with the defaults FormatterHandler and PersistingHandler
     * @param content LogContent
     */
    void log(LogContent content);

    /**
     * Log a LogContent object with specifics FormatterHandler and PersistingHandler
     * @param persistingHandlerClass Class | null to use the default PersistingHandler
     * @param formatterHandlerClass Class | null to use the default FormatterHandler
     * @param content LogContent
     */
    void log(LogContent content, Class<?> persistingHandlerClass, Class<?> formatterHandlerClass);

    /**
     * Log a string object with the default FormatterHandler and the default PersistingHandler
     * @param content Object
     * @param severity Severity
     */
    void log(Object content, Severity severity);

    /**
     * Log a string object with specifics FormatterHandler and PersistingHandler
     * @param persistingHandlerClass Class | null to use the default PersistingHandler
     * @param formatterHandlerClass Class | null to use the default FormatterHandler
     * @param content Object
     * @param severity Severity
     */
    void log(Object content, Severity severity, Class<?> persistingHandlerClass, Class<?> formatterHandlerClass);

}
