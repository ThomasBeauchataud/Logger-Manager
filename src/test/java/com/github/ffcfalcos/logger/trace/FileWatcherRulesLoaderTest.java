package com.github.ffcfalcos.logger.trace;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileWatcherRulesLoaderTest {

    @Test
    void run() {
        try {
            CsvRulesStorageHandler csvRulesStorageHandler = new CsvRulesStorageHandler();
            final String filePath = System.getProperty("user.dir") + "/src/test/resources/rules.csv";
            csvRulesStorageHandler.setFilePath(filePath, false);
            FileWatcherRulesLoader fileWatcherRulesLoader = new FileWatcherRulesLoader(csvRulesStorageHandler);
            Thread thread = new Thread(fileWatcherRulesLoader);
            thread.start();
            FileWriter csvWriter = new FileWriter(filePath);
            Rule rule = new Rule("class", "method", Entry.AFTER, null, null, false);
            csvWriter.append(String.join(",", rule.toStringList()));
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
            Thread.sleep(1000);
            assertEquals(fileWatcherRulesLoader.getRulesStorageHandler().getRules().size(), 1);
            assertTrue(fileWatcherRulesLoader.getRulesStorageHandler().getRules().get(0).equalsTo(rule));
            thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @BeforeAll
    static void cleanUp() throws IOException {
        new FileWriter(System.getProperty("user.dir") + "/src/test/resources/rules.csv").write("");
    }
}