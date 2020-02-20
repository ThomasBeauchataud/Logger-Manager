package com.github.ffcfalcos.logger.collector;

import org.json.simple.JSONArray;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.*;

@ApplicationScoped
@Default
@SuppressWarnings({"unchecked","unused"})
public class LogDataCollector {

    private static LogDataCollector instance;

    @PostConstruct
    public void initialize() {
        instance = this;
    }

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

    public void addException(Map<String, Object> logContent, Exception e) {
        if(logContent != null) {
            logContent.put("error", true);
            logContent.put("message", e.getMessage());
            logContent.put("cause", e.getCause());
        }
    }

    public Map<String, Object> close(Map<String, Object> logContent) {
        if(logContent != null) {
            logContent.put("end", new Date().getTime());
            logContent.put("time", (long) logContent.get("end") - (long) logContent.get("start"));
        }
        return logContent;
    }

    public void add(Map<String, Object> logContent, String key, Object content) {
        if(content != null) {
            if (content instanceof List) {
                JSONArray jsonArray = new JSONArray();
                for (Object object : (List<Object>) content) {
                    jsonArray.add(object.toString());
                }
                logContent.put(key, jsonArray);
            } else {
                logContent.put(key, content);
            }
        }
    }

    public static LogDataCollector getInstance() {
        if(instance == null) {
            instance = new LogDataCollector();
        }
        return instance;
    }

}
