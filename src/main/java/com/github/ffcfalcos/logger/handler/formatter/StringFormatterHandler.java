package com.github.ffcfalcos.logger.handler.formatter;

import com.github.ffcfalcos.logger.collector.LogContent;
import com.github.ffcfalcos.logger.collector.Severity;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Format a log message with the date before persist it
 */
public class StringFormatterHandler implements FormatterHandler {

    /**
     * Format a map message
     * @param logContent LogContent
     * @return String
     */
    @Override
    public String format(LogContent logContent) {
        if(logContent == null) {
            return null;
        }
        return getFormatDate() + logContent.keySet().stream().map(key -> key + "=" +
                logContent.get(key)).collect(Collectors.joining(", ", "{", "}"));
    }

    /**
     * Format an object with his severity
     * @param logContent Object
     * @param severity Severity
     * @return String
     */
    @Override
    public String format(Object logContent, Severity severity) {
        if(logContent == null) {
            return null;
        }
        String content =  getFormatDate() + " - ";
        if(severity != null) {
            content += severity.name() + " - ";
        }
        content += logContent.toString();
        return content;
    }

    private String getFormatDate() {
        return "[" + new Date().toString() + "] ";
    }

}
