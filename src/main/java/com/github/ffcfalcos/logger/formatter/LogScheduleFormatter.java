package com.github.ffcfalcos.logger.formatter;

import org.json.simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 2.0.0
 * This class permit to format a rich log message for schedules
 */
@Default
@ApplicationScoped
class LogScheduleFormatter extends LogFormatter implements LogScheduleFormatterInterface {

    /**
     * Initialize a log message with his type by using super class init(LogType)
     * @return JSONObject[]
     */
    @Override
    public List<JSONObject> init() {
        return super.init(LogType.SCHEDULE);
    }

    /**
     * Add a message saying that a Schedule or a Thread has started
     * @param logContent JSONObject[]
     * @param scheduleName String
     */
    @Override
    public void start(List<JSONObject> logContent, String scheduleName) {
        add(logContent, "request", "Starting Schedule " + scheduleName);
    }

    /**
     * Add a message saying that a Schedule or a Thread has stopped
     * @param logContent JSONObject[]
     */
    @Override
    public void end(List<JSONObject> logContent) {
        add(logContent, "response", "Success of the execution of the Schedule");
    }

}
