package com.github.ffcfalcos.logger.handler.formatter;

import java.util.Map;

public interface FormatterHandlerInterface {

    String format(Map<String, Object> logContent);

    String format(String logContent);

}
