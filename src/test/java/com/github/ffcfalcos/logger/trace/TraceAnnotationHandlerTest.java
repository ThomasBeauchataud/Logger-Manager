package com.github.ffcfalcos.logger.trace;

import com.github.ffcfalcos.logger.FilePersistingHandler;
import com.github.ffcfalcos.logger.Logger;
import com.github.ffcfalcos.logger.StringFormatterHandler;
import com.github.ffcfalcos.logger.util.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class TraceAnnotationHandlerTest {

    @Test
    void traceBeforePointcut() {
        try {
            cleanUp();
            before();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(((FilePersistingHandler) Logger.getInstance()
                    .getPersistingHandlerProvider().get(FilePersistingHandler.class)).getFilePath()));
            String line = bufferedReader.readLine();
            assertTrue(line.contains("method=before"));
            assertTrue(line.contains("type=TRACE_BEFORE"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void traceAroundPointcut() {
        try {
            cleanUp();
            around();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(((FilePersistingHandler) Logger.getInstance()
                    .getPersistingHandlerProvider().get(FilePersistingHandler.class)).getFilePath()));
            String line = bufferedReader.readLine();
            assertTrue(line.contains("method=around"));
            assertTrue(line.contains("type=TRACE_AROUND"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void traceAfterPointcut() {
        try {
            cleanUp();
            after();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(((FilePersistingHandler) Logger.getInstance()
                    .getPersistingHandlerProvider().get(FilePersistingHandler.class)).getFilePath()));
            String line = bufferedReader.readLine();
            assertTrue(line.contains("method=after"));
            assertTrue(line.contains("type=TRACE_AFTER"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void traceAfterThrowingPointcut() {
        try {
            cleanUp();
            try {
                afterThrowing();
            } catch (TestException e) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(((FilePersistingHandler) Logger.getInstance()
                        .getPersistingHandlerProvider().get(FilePersistingHandler.class)).getFilePath()));
                String line = bufferedReader.readLine();
                assertTrue(line.contains("method=afterThrowing"));
                assertTrue(line.contains("type=TRACE_AFTER_THROWING"));
                assertTrue(line.contains(e.getMessage()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void traceAfterReturningPointcut() {
        try {
            cleanUp();
            afterReturning();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(((FilePersistingHandler) Logger.getInstance()
                    .getPersistingHandlerProvider().get(FilePersistingHandler.class)).getFilePath()));
            String line = bufferedReader.readLine();
            assertTrue(line.contains("method=afterReturning"));
            assertTrue(line.contains("type=TRACE_AFTER_RETURNING"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @TraceBefore(persistingHandlerClass = FilePersistingHandler.class, formatterHandlerClass = StringFormatterHandler.class)
    void before() {
    }

    @TraceAround(persistingHandlerClass = FilePersistingHandler.class, formatterHandlerClass = StringFormatterHandler.class)
    void around() {
    }

    @TraceAfter(persistingHandlerClass = FilePersistingHandler.class, formatterHandlerClass = StringFormatterHandler.class)
    void after() {
    }

    @TraceAfterReturning(persistingHandlerClass = FilePersistingHandler.class, formatterHandlerClass = StringFormatterHandler.class)
    void afterReturning() {
    }

    @TraceAfterThrowing(persistingHandlerClass = FilePersistingHandler.class, formatterHandlerClass = StringFormatterHandler.class)
    void afterThrowing() throws TestException {
        throw new TestException("Test exception message");
    }

    @BeforeAll
    static void cleanUp() throws IOException {
        FileService.createFilePath(((FilePersistingHandler) Logger.getInstance()
                .getPersistingHandlerProvider().get(FilePersistingHandler.class)).getFilePath());
        new FileWriter(((FilePersistingHandler) Logger.getInstance()
                .getPersistingHandlerProvider().get(FilePersistingHandler.class)).getFilePath()).write("");
    }

    static class TestException extends Exception {

        public TestException(String message) {
            super(message);
        }
    }
}