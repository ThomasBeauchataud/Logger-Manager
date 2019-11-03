package com.github.ffcfalcos.logger.formatter;

import org.json.simple.JSONObject;

import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 2.0.0
 * This class permit to format a rich log message for execution methods or functions
 */
public interface LogInterceptorFormatterInterface extends LogFormatterInterface {

    /**
     * Add method parameters to the log content
     * @param logContent JSONObject[]
     * @param parameters Object[]
     */
    void addParameters(List<JSONObject> logContent, Object[] parameters);

    /**
     * Add method response to the log content
     * @param logContent JSONObject[]
     * @param response Object
     */
    void addResponse(List<JSONObject> logContent, Object response);

}
