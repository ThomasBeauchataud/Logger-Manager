package com.github.ffcfalcos.logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogContentTest {

    @Test
    void addException() {
        try {
            LogContent logContent = new LogContent(LogType.TRACE);
            logContent.addException(new Exception("Exception test"));
            assertEquals(logContent.close(Severity.DEBUG).get("message"), "Exception test");
            assertEquals(logContent.close(Severity.DEBUG).get("error"), true);
            logContent.addException(null);
            assertNull(logContent.get("message"));
            assertEquals(logContent.get("error"), true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void close() {
        try {
            LogContent logContent = new LogContent(LogType.TRACE);
            logContent.close(Severity.DEBUG);
            assertNotNull(logContent.close(Severity.DEBUG).get("end"));
            assertNotNull(logContent.close(Severity.DEBUG).get("time"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
