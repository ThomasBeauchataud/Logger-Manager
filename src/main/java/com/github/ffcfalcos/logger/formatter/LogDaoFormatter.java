package com.github.ffcfalcos.logger.formatter;

import org.json.simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to format a rich log message for execution of SQL request
 */
@Default
@ApplicationScoped
class LogDaoFormatter extends LogFormatter implements LogDaoFormatterInterface {

    /**
     * Initialize a log message with his type by using super class init(LogType)
     * @return JSONObject[]
     */
    @Override
    public List<JSONObject> init() {
        return super.init(LogType.DAO);
    }

    /**
     * Add the SQL Request to the log content
     * @param logContent JSONObject[]
     * @param request String the SQL Request
     */
    @Override
    public void addRequest(List<JSONObject> logContent, String request) {
        add(logContent,"request", request);
    }

    /**
     * Add the SQL Response to the log content
     * @param logContent JSONObject[]
     * @param response Object the SQL Response
     * The final value is response.toString()
     * If its an array, this method will generate a JSONArray and execute toString()
     *      on each element of the array
     */
    @Override
    public void addResponse(List<JSONObject> logContent, Object response) {
        add(logContent,"response", response);
    }

}
