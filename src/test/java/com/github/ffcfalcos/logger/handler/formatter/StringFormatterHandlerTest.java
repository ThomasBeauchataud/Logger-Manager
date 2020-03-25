package com.github.ffcfalcos.logger.handler.formatter;

import com.github.ffcfalcos.logger.StringFormatterHandler;
import com.github.ffcfalcos.logger.LogContent;
import com.github.ffcfalcos.logger.LogType;
import com.github.ffcfalcos.logger.Severity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringFormatterHandlerTest {

    @Test
    void testFormat1() {
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
    void testFormat2() {
        try {
            StringFormatterHandler stringFormatterHandler = new StringFormatterHandler();
            LogContent logContent = new LogContent(LogType.TRACE).close();
            logContent.put("content", "We are doing a test");
            String formattedMessage = stringFormatterHandler.format(logContent);
            assertTrue(formattedMessage.contains("We are doing a test"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}