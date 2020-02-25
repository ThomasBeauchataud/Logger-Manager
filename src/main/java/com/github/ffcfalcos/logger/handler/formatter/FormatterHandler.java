package com.github.ffcfalcos.logger.handler.formatter;

import com.github.ffcfalcos.logger.collector.LogContent;
import com.github.ffcfalcos.logger.collector.Severity;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Format a log message before persist it
 */
public interface FormatterHandler {

    /**
     * Format a LogContent message
     * @param logContent LogContent
     * @return String
     */
    String format(LogContent logContent);

    /**
     * Format a string message with his severity
     * @param logContent String
     * @param severity Severity
     * @return String
     */
    String format(String logContent, Severity severity);

}
