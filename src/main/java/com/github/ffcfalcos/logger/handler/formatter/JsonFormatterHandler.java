package com.github.ffcfalcos.logger.handler.formatter;

import com.github.ffcfalcos.logger.collector.Severity;
import org.json.simple.JSONObject;

import java.util.Map;

@SuppressWarnings("unchecked")
public class JsonFormatterHandler implements FormatterHandler {

    @Override
    public String format(Map<String, Object> logContent) {
        if(logContent == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String, Object> map : logContent.entrySet()) {
            jsonObject.put(map.getKey(), map.getValue());
        }
        return jsonObject.toJSONString();
    }

    @Override
    public String format(String logContent, Severity severity) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", logContent);
        jsonObject.put("severity", severity.name());
        return jsonObject.toJSONString();
    }

}
