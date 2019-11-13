package com.github.ffcfalcos.logger.handler.formatter;

import org.json.simple.JSONObject;

import java.util.Map;

@SuppressWarnings("unchecked")
public class JsonFormatterHandler implements FormatterHandlerInterface {

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
    public String format(String logContent) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", logContent);
        return jsonObject.toJSONString();
    }

}
