package com.github.ffcfalcos.logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LoggerStatisticsManagementTest {

    @Test
    void update() {
        try {
            LoggerStatisticsManagement loggerStatisticsManagement = new LoggerStatisticsManagement();
            loggerStatisticsManagement.setActive(true);
            String filePath = System.getProperty("user.dir") + "/src/test/resources/stats.csv";
            loggerStatisticsManagement.setFilePath(filePath, false);
            loggerStatisticsManagement.update(FilePersistingHandler.class, JsonFormatterHandler.class);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();
            assertTrue(line.contains(JsonFormatterHandler.class.getName()));
            assertTrue(line.contains(FilePersistingHandler.class.getName()));
            filePath = System.getProperty("user.dir") + "/src/test/resources/stats-switch.csv";
            loggerStatisticsManagement.setFilePath(filePath, true);
            loggerStatisticsManagement.update(RabbitMQPersistingHandler.class, StringFormatterHandler.class);
            bufferedReader = new BufferedReader(new FileReader(filePath));
            line = bufferedReader.readLine();
            assertTrue(line.contains(JsonFormatterHandler.class.getName()));
            assertTrue(line.contains(FilePersistingHandler.class.getName()));
            line = bufferedReader.readLine();
            assertTrue(line.contains(StringFormatterHandler.class.getName()));
            assertTrue(line.contains(RabbitMQPersistingHandler.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @BeforeAll
    static void cleanUp() throws IOException {
        new FileWriter(System.getProperty("user.dir") + "/src/test/resources/stats.csv").write("");
        new FileWriter(System.getProperty("user.dir") + "/src/test/resources/stats-switch.csv").write("");
    }
}