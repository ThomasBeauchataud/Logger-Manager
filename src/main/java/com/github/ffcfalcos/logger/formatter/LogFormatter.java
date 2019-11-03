package com.github.ffcfalcos.logger.formatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
abstract class LogFormatter implements LogFormatterInterface {

    /**
     * Add an exception to the log content
     *      field "message" get the value of e.getMessage()
     *      field "cause" get the value of e.getCause()
     *      field "error" get the value true
     * @param logContent JSONObject[]
     * @param e Exception
     */
    @Override
    public void addException(List<JSONObject> logContent, Exception e) {
        logContent.get(0).put("error", true);
        logContent.get(0).put("message", e.getMessage());
        logContent.get(0).put("cause", e.getCause());
    }

    /**
     * Close a log content and return the JSON String to be logged
     * @param logContent JSONObject[]
     * @return String
     */
    @Override
    public String close(List<JSONObject> logContent) {
        logContent.get(0).put("end", new Date().getTime());
        logContent.get(0).put("time", (long) logContent.get(0).get("end") - (long) logContent.get(0).get("start"));
        logContent.get(0).put("ctx", logContent.get(1));
        logContent.get(0).put("env", logContent.get(2));
        return logContent.get(0).toJSONString();
    }

    /**
     * This method is protected to not allow any class to have a free access on the log content to prevent the override
     *      a field
     * If you want to add some fields, create a new LogFormatter extending another LogFormatter and add some methods
     * @param logContent JSONObject[]
     * @param key String
     * @param content Object
     */
    void add(List<JSONObject> logContent, String key, Object content) {
        if(content instanceof List) {
            JSONArray jsonArray = new JSONArray();
            for(Object object : (List)content) {
                jsonArray.add(object.toString());
            }
            logContent.get(0).put(key, jsonArray);
        } else {
            logContent.get(0).put(key, content);
        }
    }

    /**
     * Initialize a log message with his type
     * @param logType LogType
     * @return JSONObject[]
     */
    List<JSONObject> init(LogType logType) {
        List<JSONObject> logContent = Arrays.asList(new JSONObject(), new JSONObject(), new JSONObject());
        logContent.get(0).put("type", logType.name());
        logContent.get(0).put("start", new Date().getTime());
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            logContent.get(2).put("application", env.lookup("application-name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        logContent.get(0).put("error", false);
        return logContent;
    }

}
