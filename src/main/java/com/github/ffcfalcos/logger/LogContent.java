package com.github.ffcfalcos.logger;

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
     *
     * @param logType String
     */
    public LogContent(LogType logType) {
        if (logType == null) {
            logType = LogType.UNKNOWN;
        }
        put("type", logType.name());
        put("start", new Date().getTime());
        put("error", false);
    }

    /**
     * Add an exception in the log content
     *
     * @param e Exception
     */
    public void addException(Exception e) {
        if (e != null) {
            put("message", e.getMessage());
            put("cause", e.getCause());
        } else {
            if (get("message") != null) {
                remove("message");
            }
            if (get("cause") != null) {
                remove("cause");
            }
        }
        put("error", true);
    }

    /**
     * Close the log content and add some default values
     *
     * @param severity Severity
     * @return LogContent
     */
    public LogContent close(Severity severity) {
        put("end", new Date().getTime());
        put("severity", severity.name());
        put("time", (long) get("end") - (long) get("start"));
        return this;
    }
}
