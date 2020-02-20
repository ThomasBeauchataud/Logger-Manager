package com.github.ffcfalcos.logger.handler.formatter;

import com.github.ffcfalcos.logger.collector.Severity;

import java.util.Map;

public interface FormatterHandler {

    String format(Map<String, Object> logContent);

    String format(String logContent, Severity severity);

}
