package com.github.ffcfalcos.logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringFormatterHandlerTest {

    @Test
    void format1() {
        try {
            StringFormatterHandler stringFormatterHandler = new StringFormatterHandler();
            String formattedMessage = stringFormatterHandler.format("We are doing a test", Severity.DEBUG);
            assertTrue(formattedMessage.contains("We are doing a test"));
            assertTrue(formattedMessage.contains(Severity.DEBUG.name()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void format2() {
        try {
            StringFormatterHandler stringFormatterHandler = new StringFormatterHandler();
            LogContent logContent = new LogContent(LogType.TRACE).close(Severity.DEBUG);
            logContent.put("content", "We are doing a test");
            String formattedMessage = stringFormatterHandler.format(logContent);
            assertTrue(formattedMessage.contains("We are doing a test"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}