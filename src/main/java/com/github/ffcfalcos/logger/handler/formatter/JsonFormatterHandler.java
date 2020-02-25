package com.github.ffcfalcos.logger.handler.formatter;

import com.github.ffcfalcos.logger.collector.LogContent;
import com.github.ffcfalcos.logger.collector.Severity;
import org.json.JSONObject;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Format a log message to Json before persist it
 */
public class JsonFormatterHandler implements FormatterHandler {

    /**
     * Format a LogContent message
     * @param logContent LogContent
     * @return String
     */
    @Override
    public String format(LogContent logContent) {
        if(logContent == null) {
            return null;
        }
        return new JSONObject(logContent).toString();
    }

    /**
     * Format a string message with his severity
     * @param logContent String
     * @param severity Severity
     * @return String
     */
    @Override
    public String format(String logContent, Severity severity) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", logContent);
        if(severity != null) {
            jsonObject.put("severity", severity.name());
        }
        return jsonObject.toString();
    }

}
