package com.github.ffcfalcos.logger.formatter;

import org.json.simple.JSONObject;

import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to format a rich log message for execution of SQL request
 */
public interface LogDaoFormatterInterface extends LogFormatterInterface {

    List<JSONObject> init();

    /**
     * Add the SQL Request to the log content
     * @param logContent JSONObject[]
     * @param request String the SQL Request
     */
    void addRequest(List<JSONObject> logContent, String request);

    /**
     * Add the SQL Response to the log content
     * @param logContent JSONObject[]
     * @param response Object the SQL Response
     * The final value is response.toString()
     * If its an array, this method will generate a JSONArray and execute toString()
     *      on each element of the array
     */
    void addResponse(List<JSONObject> logContent, Object response);

}
