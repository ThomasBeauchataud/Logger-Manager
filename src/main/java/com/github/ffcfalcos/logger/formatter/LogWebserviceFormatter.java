package com.github.ffcfalcos.logger.formatter;

import org.json.simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 2.0.0
 * This class permit to format a rich log message for webservice handlers
 */
@Default
@ApplicationScoped
class LogWebserviceFormatter extends LogFormatter implements LogWebserviceFormatterInterface {

    /**
     * Initialize a log message with his type by using super class init(LogType)
     * @return JSONObject[]
     */
    @Override
    public List<JSONObject> init() {
        return super.init(LogType.WEBSERVICE);
    }

    /**
     * Add an incoming webservice Request to the log content
     * @param logContent JSONObject[]
     * @param url String url of the webservice
     * @param request String to body of the request
     * @param method String the method of the request
     */
    @Override
    public void addRequest(List<JSONObject> logContent, String url, String request, String method) {
        add(logContent, "url", url);
        add(logContent, "content", request);
        add(logContent, "method", method);
    }

    /**
     * Add an outbound webservice Response to the log content
     * @param logContent JSONObject[]
     * @param response String the body of the response
     */
    @Override
    public void addResponse(List<JSONObject> logContent, String response) {
        add(logContent, "response", response);
    }

}
