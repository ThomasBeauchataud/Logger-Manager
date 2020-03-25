package com.github.ffcfalcos.logger.handler.persisting;

import com.github.ffcfalcos.logger.FilePersistingHandler;
import com.github.ffcfalcos.logger.PersistingHandler;
import com.github.ffcfalcos.logger.RabbitMQPersistingHandler;
import com.github.ffcfalcos.logger.SystemOutPersistingHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PersistingHandlerTest {

    @Test
    public void run() {
        runInternal(new FilePersistingHandler());
        runInternal(new SystemOutPersistingHandler());
        runInternal(new RabbitMQPersistingHandler());
    }

    private void runInternal(PersistingHandler persistingHandler) {
        assertDoesNotThrow(() -> persistingHandler.persist("hello"),
                "Trying to persist a string message");
        assertDoesNotThrow(() -> persistingHandler.persist(null),
                "Trying to persist a null message");
        assertDoesNotThrow(() -> persistingHandler.persist(""),
                "Trying to persist an empty message");
    }

}
