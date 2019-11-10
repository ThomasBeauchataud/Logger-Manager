package com.github.ffcfalcos.logger.formatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to format a rich log message for execution methods or functions
 */
@Default
@ApplicationScoped
@SuppressWarnings("unchecked")
class LogInterceptorFormatter extends LogFormatter implements LogInterceptorFormatterInterface {

    /**
     * Initialize a log message with his type by using super class init(LogType)
     * @return JSONObject[]
     */
    @Override
    public List<JSONObject> init() {
        return super.init(LogType.INTERCEPTOR);
    }

    /**
     * Add method parameters to the log content
     * @param logContent JSONObject[]
     * @param parameters Object[]
     */
    @Override
    public void addParameters(List<JSONObject> logContent, Object[] parameters) {
        JSONArray jsonArray = new JSONArray();
        for(Object object : parameters) {
            jsonArray.add(object.toString());
        }
        logContent.get(0).put("parameters", jsonArray);
    }

    /**
     * Add method response to the log content
     * @param logContent JSONObject[]
     * @param response Object
     */
    @Override
    public void addResponse(List<JSONObject> logContent, Object response) {
        logContent.get(0).put("response", response.toString());
    }

}
