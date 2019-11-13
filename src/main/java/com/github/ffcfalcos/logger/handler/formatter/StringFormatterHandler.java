package com.github.ffcfalcos.logger.handler.formatter;

import java.util.Map;
import java.util.stream.Collectors;

public class StringFormatterHandler implements FormatterHandlerInterface {

    @Override
    public String format(Map<String, Object> logContent) {
        if(logContent == null) {
            return null;
        }
        return logContent.keySet().stream().map(key -> key + "=" + logContent.get(key)).collect(Collectors.joining(", ", "{", "}"));
    }

    @Override
    public String format(String logContent) {
        return logContent;
    }

}
