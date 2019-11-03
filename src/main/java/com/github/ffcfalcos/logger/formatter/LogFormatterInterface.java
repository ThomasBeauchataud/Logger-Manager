package com.github.ffcfalcos.logger.formatter;

import org.json.simple.JSONObject;

import java.util.List;

interface LogFormatterInterface {

    /**
     * Initialize a log message with his type by using super class init(LogType)
     * @return JSONObject[]
     */
    List<JSONObject> init();

    /**
     * Add an exception to the log content
     *      field "message" get the value of e.getMessage()
     *      field "cause" get the value of e.getCause()
     *      field "error" get the value true
     * @param logContent JSONObject[]
     * @param e Exception
     */
    void addException(List<JSONObject> logContent, Exception e);

    /**
     * Close a log content and return the JSON String to be logged
     * @param logContent JSONObject[]
     * @return String
     */
    String close(List<JSONObject> logContent);

}
