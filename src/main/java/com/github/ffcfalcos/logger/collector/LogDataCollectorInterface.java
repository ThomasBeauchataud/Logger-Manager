package com.github.ffcfalcos.logger.collector;

import java.util.Map;

public interface LogDataCollectorInterface {

    Map<String, Object> init(String logType);

    void addException(Map<String, Object> logContent, Exception e);

    void add(Map<String, Object> logContent, String key, Object content);

    Map<String, Object> close(Map<String, Object> logContent);

}
