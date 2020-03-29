package com.github.ffcfalcos.logger.trace;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SleepRulesLoaderTest {

    @Test
    void run() {
        try {
            CsvRulesStorageHandler csvRulesStorageHandler = new CsvRulesStorageHandler();
            final String filePath = System.getProperty("user.dir") + "/src/test/resources/rules.csv";
            csvRulesStorageHandler.setFilePath(filePath, false);
            SleepRulesLoader sleepRulesLoader = new SleepRulesLoader(csvRulesStorageHandler);
            FileWriter csvWriter = new FileWriter(filePath);
            Rule rule = new Rule("class", "method", Entry.AFTER, null, null, false);
            csvWriter.append(String.join(",", rule.toStringList()));
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
            Thread thread = new Thread(sleepRulesLoader);
            thread.start();
            Thread.sleep(1000);
            assertEquals(sleepRulesLoader.getRulesStorageHandler().getRules().size(), 1);
            assertTrue(sleepRulesLoader.getRulesStorageHandler().getRules().get(0).equalsTo(rule));
            thread.interrupt();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @BeforeAll
    static void cleanUp() throws IOException {
        new FileWriter(System.getProperty("user.dir") + "/src/test/resources/rules.csv").write("");
    }
}