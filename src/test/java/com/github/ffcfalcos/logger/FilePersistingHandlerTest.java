package com.github.ffcfalcos.logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class FilePersistingHandlerTest {

    @Test
    void persist() {
        try {
            FilePersistingHandler filePersistingHandler = new FilePersistingHandler();
            final String filePath = System.getProperty("user.dir") + "/src/test/resources/file-test.log";
            filePersistingHandler.setFilePath(filePath);
            filePersistingHandler.persist("Here is a test message");
            File file = new File(filePath);
            file.deleteOnExit();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            assertEquals(line, "Here is a test message");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @BeforeAll
    static void cleanUp() throws IOException {
        new FileWriter(System.getProperty("user.dir") + "/src/test/resources/test-log.log").write("");
    }
}