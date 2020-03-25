package com.github.ffcfalcos.logger.handler.formatter;

import com.github.ffcfalcos.logger.JsonFormatterHandler;
import com.github.ffcfalcos.logger.LogContent;
import com.github.ffcfalcos.logger.LogType;
import com.github.ffcfalcos.logger.Severity;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFormatterHandlerTest {

    @Test
    void testFormat1() {
        try {
            JsonFormatterHandler jsonFormatterHandler = new JsonFormatterHandler();
            String formattedMessage = jsonFormatterHandler.format("We are doing a test", Severity.DEBUG);
            JSONObject jsonObject = new JSONObject(formattedMessage);
            assertEquals(jsonObject.get("severity"), Severity.DEBUG.name());
            assertEquals(jsonObject.get("content"), "We are doing a test");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testFormat2() {
        try {
            JsonFormatterHandler jsonFormatterHandler = new JsonFormatterHandler();
            LogContent logContent = new LogContent(LogType.TRACE).close();
            String formattedMessage = jsonFormatterHandler.format(logContent);
            JSONObject jsonObject = new JSONObject(formattedMessage);
            assertEquals(jsonObject.get("error"), false);
            assertEquals(jsonObject.get("type"), LogType.TRACE.name());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}