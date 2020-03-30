package com.github.ffcfalcos.logger.trace;

import com.github.ffcfalcos.logger.FilePersistingHandler;
import com.github.ffcfalcos.logger.JsonFormatterHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvRulesStorageHandlerTest {

    @Test
    void getRules() {
        try {
            cleanUp();
            CsvRulesStorageHandler csvRulesStorageHandler = new CsvRulesStorageHandler();
            final String filePath = System.getProperty("user.dir") + "/src/test/resources/rules.csv";
            csvRulesStorageHandler.setFilePath(filePath, false);
            FileWriter csvWriter = new FileWriter(filePath);
            Rule rule = new Rule("class", "method", Entry.AFTER, null, null, false);
            csvWriter.append(String.join(",", rule.toStringList()));
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
            assertEquals(csvRulesStorageHandler.getRules().size(), 1);
            assertTrue(csvRulesStorageHandler.getRules().get(0).equalsTo(rule));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void removeRules() {
        try {
            cleanUp();
            CsvRulesStorageHandler csvRulesStorageHandler = new CsvRulesStorageHandler();
            final String filePath = System.getProperty("user.dir") + "/src/test/resources/rules.csv";
            csvRulesStorageHandler.setFilePath(filePath, false);
            FileWriter csvWriter = new FileWriter(filePath);
            Rule rule = new Rule("class", "method", Entry.AFTER, null, null, false);
            csvWriter.append(String.join(",", rule.toStringList()));
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
            assertEquals(csvRulesStorageHandler.getRules().size(), 1);
            assertTrue(csvRulesStorageHandler.getRules().get(0).equalsTo(rule));
            csvRulesStorageHandler.removeRules(Collections.singletonList(rule));
            assertEquals(csvRulesStorageHandler.getRules().size(), 0);
            assertDoesNotThrow(() -> csvRulesStorageHandler.removeRules(Collections.singletonList(rule)));
            assertDoesNotThrow(() -> csvRulesStorageHandler.removeRules(Collections.singletonList(null)));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void addRules() {
        try {
            cleanUp();
            CsvRulesStorageHandler csvRulesStorageHandler = new CsvRulesStorageHandler();
            final String filePath = System.getProperty("user.dir") + "/src/test/resources/rules.csv";
            csvRulesStorageHandler.setFilePath(filePath, false);
            List<Rule> rules = Arrays.asList(
                    new Rule("class1", "method1", Entry.AFTER, null, null, false),
                    new Rule("class2", "method2", Entry.BEFORE, FilePersistingHandler.class, null, false),
                    new Rule("class3", "method3", Entry.AROUND, null, JsonFormatterHandler.class, false),
                    new Rule("class4", "method4", Entry.AFTER_THROWING, null, JsonFormatterHandler.class, false),
                    new Rule("class5", "method5", Entry.AFTER_RETURNING, null, JsonFormatterHandler.class, false),
                    new Rule("class6", "method6", Entry.AFTER_RETURNING, null, JsonFormatterHandler.class, false)
            );
            assertDoesNotThrow(() -> csvRulesStorageHandler.addRules(rules));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            int count = 0;
            while(bufferedReader.readLine() != null) {
                ++count;
            }
            assertEquals(count, 6);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void setFilePath() {
        try {
            cleanUp();
            CsvRulesStorageHandler csvRulesStorageHandler = new CsvRulesStorageHandler();
            String filePath = System.getProperty("user.dir") + "/src/test/resources/rules.csv";
            csvRulesStorageHandler.setFilePath(filePath, false);
            Rule rule = new Rule("class1", "method1", Entry.AFTER, null, null, false);
            assertDoesNotThrow(() -> csvRulesStorageHandler.addRules(Collections.singletonList(rule)));
            filePath = System.getProperty("user.dir") + "/src/test/resources/rules-switch.csv";
            csvRulesStorageHandler.setFilePath(filePath, true);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            assertTrue(bufferedReader.readLine().contains("method1"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @BeforeAll
    static void cleanUp() throws IOException {
        new FileWriter(System.getProperty("user.dir") + "/src/test/resources/rules.csv").write("");
        new FileWriter(System.getProperty("user.dir") + "/src/test/resources/rules-switch.csv").write("");
    }
}