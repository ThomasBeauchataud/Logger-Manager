package com.github.ffcfalcos.logger.collector;

import com.github.ffcfalcos.logger.LogContent;
import com.github.ffcfalcos.logger.LogType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogContentTest {

    @Test
    void addException() {
        try {
            LogContent logContent = new LogContent(LogType.TRACE);
            logContent.addException(new Exception("Exception test"));
            assertEquals(logContent.close().get("message"), "Exception test");
            assertEquals(logContent.close().get("error"), true);
            logContent.addException(null);
            assertNull(logContent.get("message"));
            assertEquals(logContent.get("error"), true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void close() {
        try {
            LogContent logContent = new LogContent(LogType.TRACE);
            logContent.close();
            assertNotNull(logContent.close().get("end"));
            assertNotNull(logContent.close().get("time"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
