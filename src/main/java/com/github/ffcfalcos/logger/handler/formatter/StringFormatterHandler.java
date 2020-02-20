package com.github.ffcfalcos.logger.handler.formatter;

import com.github.ffcfalcos.logger.collector.Severity;

import java.util.Map;
import java.util.stream.Collectors;

public class StringFormatterHandler implements FormatterHandler {

    @Override
    public String format(Map<String, Object> logContent) {
        if(logContent == null) {
            return null;
        }
        return logContent.keySet().stream().map(key -> key + "=" + logContent.get(key)).collect(Collectors.joining(", ", "{", "}"));
    }

    @Override
    public String format(String logContent, Severity severity) {
        return severity.name() + " - " + logContent;
    }

}
