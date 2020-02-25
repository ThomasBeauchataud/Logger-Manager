package com.github.ffcfalcos.logger.collector;

import java.util.Date;
import java.util.HashMap;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * HashMap object with methods to generate some defaults fields for a log message
 */
public class LogContent extends HashMap<String, Object> {

    /**
     * LogContent Constructor
     * Initialize the creation of the map object with default data
     * @param logType String
     */
    public LogContent(String logType) {
        put("type", logType);
        put("start", new Date().getTime());
        put("error", false);
    }

    /**
     * Add an exception in the log content
     * @param e Exception
     */
    public void addException(Exception e) {
        put("error", true);
        put("message", e.getMessage());
        put("cause", e.getCause());
    }

    /**
     * Close the log content and add some default values
     * @return LogContent
     */
    public LogContent close() {
        put("end", new Date().getTime());
        put("time", (long) get("end") - (long) get("start"));
        return this;
    }
}
