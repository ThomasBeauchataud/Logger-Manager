package com.github.ffcfalcos.logger.formatter;

import org.apache.http.Header;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to format a rich log message for webservice calls
 */
@Default
@ApplicationScoped
@SuppressWarnings("unchecked")
class LogWebserviceCallFormatter extends LogFormatter implements LogWebserviceCallFormatterInterface {

    /**
     * Initialize a log message with his type by using super class init(LogType)
     * @return JSONObject[]
     */
    @Override
    public List<JSONObject> init() {
        return super.init(LogType.WEBSERVICE_CALL);
    }

    /**
     * Add a webservice Request to the log content
     * @param logContent JSONObject[]
     * @param url String url of the webservice
     * @param request String to body of the request
     * @param headers Header[] headers of the request
     * @param method String the method of the request
     */
    @Override
    public void addRequest(List<JSONObject> logContent, String url, String request, Header[] headers, String method) {
        add(logContent, "url", url);
        add(logContent, "content", request);
        add(logContent, "method", method);
        this.addHeaders(logContent, headers);
    }

    /**
     * Add a webservice Response to the log content
     * @param logContent JSONObject[]
     * @param response String the body of the response
     */
    @Override
    public void addResponse(List<JSONObject> logContent, String response) {
        add(logContent, "response", response);
    }

    /**
     * Format the headers as a JSONArray
     * @param logContent JSONObject[]
     * @param headers Header[]
     */
    private void addHeaders(List<JSONObject> logContent, Header[] headers) {
        JSONArray jsonArray = new JSONArray();
        for(Header header : headers) {
            jsonArray.add(header.getName() + ": " + header.getValue());
        }
        add(logContent, "headers", jsonArray);
    }

}
