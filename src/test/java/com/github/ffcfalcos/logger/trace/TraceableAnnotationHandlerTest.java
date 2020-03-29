package com.github.ffcfalcos.logger.trace;

import com.github.ffcfalcos.logger.FilePersistingHandler;
import com.github.ffcfalcos.logger.Logger;
import com.github.ffcfalcos.logger.StringFormatterHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TraceableAnnotationHandlerTest {

    @Test
    void traceablePointcut() {
        traceable();
        try {
            traceable();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(((FilePersistingHandler) Logger.getInstance()
                    .getPersistingHandlerProvider().get(FilePersistingHandler.class)).getFilePath()));
            String line = bufferedReader.readLine();
            assertNull(line);
            CsvRulesStorageHandler csvRulesStorageHandler = new CsvRulesStorageHandler();
            csvRulesStorageHandler.addRules(Collections.singletonList(new Rule(
                    TraceableAnnotationHandlerTest.class.getName(),
                    "traceable", Entry.BEFORE, FilePersistingHandler.class, StringFormatterHandler.class,
                    false
            )));
            Thread.sleep(1000);
            traceable();
            bufferedReader = new BufferedReader(new FileReader(((FilePersistingHandler) Logger.getInstance()
                    .getPersistingHandlerProvider().get(FilePersistingHandler.class)).getFilePath()));
            line = bufferedReader.readLine();
            assertTrue(line.contains("method=traceable"));
            assertTrue(line.contains("type=TRACE_BEFORE"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Traceable
    void traceable() {
    }

    @BeforeAll
    static void cleanUp() throws IOException {
        new FileWriter(((FilePersistingHandler) Logger.getInstance()
                .getPersistingHandlerProvider().get(FilePersistingHandler.class)).getFilePath()).write("");
        new FileWriter(new CsvRulesStorageHandler().getFilePath()).write("");
    }
}