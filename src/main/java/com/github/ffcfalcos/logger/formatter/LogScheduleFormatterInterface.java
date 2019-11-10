package com.github.ffcfalcos.logger.formatter;

import org.json.simple.JSONObject;

import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to format a rich log message for schedules
 */
public interface LogScheduleFormatterInterface extends LogFormatterInterface {

    /**
     * Add a message saying that a Schedule or a Thread has started
     * @param logContent JSONObject[]
     * @param scheduleName String
     */
    void start(List<JSONObject> logContent, String scheduleName);

    /**
     * Add a message saying that a Schedule or a Thread has stopped
     * @param logContent JSONObject[]
     */
    void end(List<JSONObject> logContent);

}
