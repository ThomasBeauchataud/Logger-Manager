package com.github.ffcfalcos.logger;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFormatterHandlerTest {

    @Test
    void format1() {
        try {
            JsonFormatterHandler jsonFormatterHandler = new JsonFormatterHandler();
            String formattedMessage = jsonFormatterHandler.format("We are doing a test", Severity.DEBUG);
            JSONObject jsonObject = new JSONObject(formattedMessage);
            assertEquals(jsonObject.get("severity"), Severity.DEBUG.name());
            assertEquals(jsonObject.get("content"), "We are doing a test");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void format2() {
        try {
            JsonFormatterHandler jsonFormatterHandler = new JsonFormatterHandler();
            LogContent logContent = new LogContent(LogType.TRACE).close(Severity.DEBUG);
            String formattedMessage = jsonFormatterHandler.format(logContent);
            JSONObject jsonObject = new JSONObject(formattedMessage);
            assertEquals(jsonObject.get("error"), false);
            assertEquals(jsonObject.get("type"), LogType.TRACE.name());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}