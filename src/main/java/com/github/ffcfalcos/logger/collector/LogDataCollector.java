package com.github.ffcfalcos.logger.collector;

import org.json.simple.JSONArray;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.*;

@ApplicationScoped
@Default
@SuppressWarnings({"unchecked"})
public class LogDataCollector implements LogDataCollectorInterface {

    @Override
    public Map<String, Object> init(String logType) {
        Map<String, Object> logContent = new HashMap<>();
        logContent.put("type", logType);
        logContent.put("start", new Date().getTime());
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            logContent.put("application", env.lookup("application-name"));
        } catch (Exception e) {
            logContent.put("application", "System");
        }
        logContent.put("error", false);
        return logContent;
    }

    @Override
    public void addException(Map<String, Object> logContent, Exception e) {
        if(logContent != null) {
            logContent.put("error", true);
            logContent.put("message", e.getMessage());
            logContent.put("cause", e.getCause());
        }
    }

    @Override
    public Map<String, Object> close(Map<String, Object> logContent) {
        if(logContent != null) {
            logContent.put("end", new Date().getTime());
            logContent.put("time", (long) logContent.get("end") - (long) logContent.get("start"));
        }
        return logContent;
    }

    @Override
    public void add(Map<String, Object> logContent, String key, Object content) {
        if(content != null) {
            if (content instanceof List) {
                JSONArray jsonArray = new JSONArray();
                for (Object object : (List) content) {
                    jsonArray.add(object.toString());
                }
                logContent.put(key, jsonArray);
            } else {
                logContent.put(key, content);
            }
        }
    }

}
