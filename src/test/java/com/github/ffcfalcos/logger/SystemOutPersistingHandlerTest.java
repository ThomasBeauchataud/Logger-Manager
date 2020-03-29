package com.github.ffcfalcos.logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemOutPersistingHandlerTest {

    @Test
    void persist() {
        try {
            SystemOutPersistingHandler systemOutPersistingHandler = new SystemOutPersistingHandler();
            systemOutPersistingHandler.persist("Here is a test message");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}