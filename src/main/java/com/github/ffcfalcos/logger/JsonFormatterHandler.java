package com.github.ffcfalcos.logger;

import org.json.JSONObject;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Format a log message to Json before persist it
 */
public class JsonFormatterHandler implements FormatterHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public String format(LogContent logContent) {
        if(logContent == null) {
            return null;
        }
        return new JSONObject(logContent).toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String format(Object logContent, Severity severity) {
        if(logContent == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", logContent.toString());
        if(severity != null) {
            jsonObject.put("severity", severity.name());
        }
        return jsonObject.toString();
    }

}
