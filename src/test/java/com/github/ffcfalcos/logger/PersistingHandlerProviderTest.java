package com.github.ffcfalcos.logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersistingHandlerProviderTest {

    @Test
    void get() {
        try {
            PersistingHandlerProvider persistingHandlerProvider = new PersistingHandlerProvider();
            PersistingHandler persistingHandler = persistingHandlerProvider.get(FilePersistingHandler.class);
            assertEquals(persistingHandler.getClass(), FilePersistingHandler.class);
            assertDoesNotThrow(() -> persistingHandlerProvider.get(null));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void setDefault() {
        try {
            PersistingHandlerProvider persistingHandlerProvider = new PersistingHandlerProvider();
            persistingHandlerProvider.setDefault(FilePersistingHandler.class);
            assertEquals(persistingHandlerProvider.get(void.class).getClass(), FilePersistingHandler.class);
            assertDoesNotThrow(() -> persistingHandlerProvider.setDefault(null));
            assertEquals(persistingHandlerProvider.get(void.class).getClass(), FilePersistingHandler.class);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void add() {
        try {
            class TestPersistingHandler implements PersistingHandler {
                @Override
                public void persist(String content) {
                }
            }
            PersistingHandlerProvider persistingHandlerProvider = new PersistingHandlerProvider();
            persistingHandlerProvider.add(new TestPersistingHandler());
            assertEquals(persistingHandlerProvider.get(TestPersistingHandler.class).getClass(), TestPersistingHandler.class);
            assertDoesNotThrow(() -> persistingHandlerProvider.add(null));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}