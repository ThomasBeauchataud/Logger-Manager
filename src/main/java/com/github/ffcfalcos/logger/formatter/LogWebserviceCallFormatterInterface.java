package com.github.ffcfalcos.logger.formatter;

import org.apache.http.Header;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 2.0.0
 * This class permit to format a rich log message for webservice calls
 */
public interface LogWebserviceCallFormatterInterface extends LogFormatterInterface {

    /**
     * Add a webservice Request to the log content
     * @param logContent JSONObject[]
     * @param url String url of the webservice
     * @param request String to body of the request
     * @param headers Header[] headers of the request
     * @param method String the method of the request
     */
    void addRequest(List<JSONObject> logContent, String url, String request, Header[] headers, String method);

    /**
     * Add a webservice Response to the log content
     * @param logContent JSONObject[]
     * @param response String the body of the response
     */
    void addResponse(List<JSONObject> logContent, String response);

}
