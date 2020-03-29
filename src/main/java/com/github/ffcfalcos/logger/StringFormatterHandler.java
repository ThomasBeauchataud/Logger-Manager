package com.github.ffcfalcos.logger;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Format a log message with the date before persist it
 */
public class StringFormatterHandler implements FormatterHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public String format(LogContent logContent) {
        if (logContent == null) {
            return null;
        }
        return getFormatDate() + logContent.keySet().stream().map(key -> key + "=" +
                logContent.get(key)).collect(Collectors.joining(", ", "{", "}"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String format(Object logContent, Severity severity) {
        if (logContent == null) {
            return null;
        }
        String content = getFormatDate() + " - ";
        if (severity != null) {
            content += severity.name() + " - ";
        }
        content += logContent.toString();
        return content;
    }

    /**
     * Format a date to string
     *
     * @return String
     */
    private String getFormatDate() {
        return "[" + new Date().toString() + "] ";
    }

}
