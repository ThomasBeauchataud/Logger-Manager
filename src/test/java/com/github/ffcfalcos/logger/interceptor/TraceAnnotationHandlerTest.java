package com.github.ffcfalcos.logger.interceptor;

import com.github.ffcfalcos.logger.Logger;
import com.github.ffcfalcos.logger.LoggerInterface;
import com.github.ffcfalcos.logger.LogType;
import com.github.ffcfalcos.logger.FilePersistingHandler;
import com.github.ffcfalcos.logger.util.FilePathService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TraceAnnotationHandlerTest {

    @Test
    public void run() {
        final String filePath = System.getProperty("user.dir") + "/" + UUID.randomUUID().toString() + "test.log";
        LoggerInterface logger = Logger.getInstance();
        FilePersistingHandler filePersistingHandler = (FilePersistingHandler) logger.getPersistingHandlerProvider().get(FilePersistingHandler.class);
        filePersistingHandler.setFilePath(filePath);
        UserTest user = new UserTest();
        user.isValidFirstUsername("username");
        user.isValidSecondUsername("username");
        user.isValidThirdUsername("username");
        user.isValidFourthUsername("username");
        try {
            user.isValidFifthUsername();
        } catch (Exception e) { }
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
            String line;
            int lineId = 0;
            while ((line = fileReader.readLine()) != null) {
                if(lineId == 1) {
                    JSONObject json = new JSONObject(line);
                    assertEquals(json.get("type"), LogType.TRACE_BEFORE.name(), "TraceBefore annotation handler is not working");
                    assertNotNull(json.get("parameters"), "TraceBefore annotation handler has not logged parameters");
                }
                if(lineId == 2) {
                    JSONObject json = new JSONObject(line);
                    assertEquals(json.get("type"), LogType.TRACE_AFTER.name(), "TraceAfter annotation handler is not working");
                    assertNotNull(json.get("parameters"), "TraceAfter annotation handler has not logged parameters");
                }
                if(lineId == 3) {
                    JSONObject json = new JSONObject(line);
                    assertEquals(json.get("type"), LogType.TRACE_AROUND.name(), "TraceAround annotation handler is not working");
                    assertNotNull(json.get("parameters"), "TraceAround annotation handler has not logged parameters");
                    assertNotNull(json.get("response"), "TraceAround annotation handler has not logged the method response");
                    assertNotNull(json.get("target-context"), "Context logging is not working");
                }
                if(lineId == 4) {
                    JSONObject json = new JSONObject(line);
                    assertEquals(json.get("type"), LogType.TRACE_AFTER_RETURNING.name());
                    assertNotNull(json.get("parameters"), "TraceAfterReturning annotation handler has not logged parameters");
                    assertNotNull(json.get("response"), "TraceAfterReturning annotation handler has not logged the method response");
                }
                if(lineId == 5) {
                    JSONObject json = new JSONObject(line);
                    assertEquals(json.get("type"), LogType.TRACE_AFTER_THROWING.name());
                    assertNotNull(json.get("parameters"), "TraceAfterThrowing annotation handler has not logged parameters");
                    assertNotNull(json.get("message"), "TraceAfterThrowing annotation handler has not logged exception");
                }
                ++lineId;
            }
            assertEquals(lineId, 6, "A Trace annotation is not working");
        } catch (Exception e) { }
        FilePathService.deleteFile(filePath);
    }


}
