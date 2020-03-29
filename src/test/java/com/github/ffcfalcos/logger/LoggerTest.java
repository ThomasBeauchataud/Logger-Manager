package com.github.ffcfalcos.logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    @Test
    void getFormatterHandlerProvider() {
        try {
            Logger logger = new Logger();
            assertEquals(logger.getFormatterHandlerProvider().getClass(), FormatterHandlerProvider.class);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getPersistingHandlerProvider() {
        try {
            Logger logger = new Logger();
            assertEquals(logger.getPersistingHandlerProvider().getClass(), PersistingHandlerProvider.class);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getLoggerStatisticsManagement() {
        try {
            Logger logger = new Logger();
            assertEquals(logger.getLoggerStatisticsManagement().getClass(), LoggerStatisticsManagement.class);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void log1() {
        try {
            Logger logger = new Logger();
            String filePath = System.getProperty("user.dir") + "/src/test/resources/logger-test.log";
            ((FilePersistingHandler)logger.getPersistingHandlerProvider()
                    .get(FilePersistingHandler.class)).setFilePath(filePath);
            logger.log("Here is my log message", Severity.DEBUG, FilePersistingHandler.class, StringFormatterHandler.class);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();
            assertTrue(line.contains("Here is my log message"));
            assertTrue(line.contains(Severity.DEBUG.name()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void log2() {
        try {
            Logger logger = new Logger();
            String filePath = System.getProperty("user.dir") + "/src/test/resources/logger-test.log";
            ((FilePersistingHandler)logger.getPersistingHandlerProvider()
                    .get(FilePersistingHandler.class)).setFilePath(filePath);
            LogContent logContent = new LogContent(LogType.TRACE);
            logger.log(logContent.close(Severity.DEBUG), FilePersistingHandler.class, StringFormatterHandler.class);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();
            assertTrue(line.contains("Here is my log message"));
            assertTrue(line.contains(Severity.DEBUG.name()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void log3() {
        try {
            Logger logger = new Logger();
            String filePath = System.getProperty("user.dir") + "/src/test/resources/logger-test.log";
            ((FilePersistingHandler)logger.getPersistingHandlerProvider()
                    .get(FilePersistingHandler.class)).setFilePath(filePath);
            logger.getPersistingHandlerProvider().setDefault(FilePersistingHandler.class);
            logger.getFormatterHandlerProvider().setDefault(StringFormatterHandler.class);
            logger.log("Here is my log message", Severity.DEBUG);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();
            assertTrue(line.contains("Here is my log message"));
            assertTrue(line.contains(Severity.DEBUG.name()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void log4() {
        try {
            Logger logger = new Logger();
            String filePath = System.getProperty("user.dir") + "/src/test/resources/logger-test.log";
            ((FilePersistingHandler)logger.getPersistingHandlerProvider()
                    .get(FilePersistingHandler.class)).setFilePath(filePath);
            LogContent logContent = new LogContent(LogType.TRACE);
            logger.getPersistingHandlerProvider().setDefault(FilePersistingHandler.class);
            logger.getFormatterHandlerProvider().setDefault(StringFormatterHandler.class);
            logger.log(logContent.close(Severity.DEBUG));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();
            assertTrue(line.contains("Here is my log message"));
            assertTrue(line.contains(Severity.DEBUG.name()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @BeforeAll
    static void cleanUp() throws IOException {
        new FileWriter(System.getProperty("user.dir") + "/src/test/resources/logger-test.log").write("");
    }
}