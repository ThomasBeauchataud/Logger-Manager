package com.github.ffcfalcos.logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Provides Persisting Handlers
 */
@SuppressWarnings("unused")
public class PersistingHandlerProvider implements HandlerProvider<PersistingHandler> {

    private PersistingHandler defaultPersistingHandler;
    private List<PersistingHandler> persistingHandlers;

    /**
     * PersistingHandlerProvider Constructor
     */
    public PersistingHandlerProvider() {
        defaultPersistingHandler = new FilePersistingHandler();
        persistingHandlers = new ArrayList<>();
        persistingHandlers.add(defaultPersistingHandler);
        persistingHandlers.add(new RabbitMQPersistingHandler());
        persistingHandlers.add(new SystemOutPersistingHandler());
    }

    /**
     * {@inheritDoc}
     */
    public PersistingHandler get(Class<?> persistingHandlerClass) {
        if (persistingHandlerClass == null) {
            return defaultPersistingHandler;
        }
        for (PersistingHandler persistingHandler : this.persistingHandlers) {
            if (persistingHandler.getClass().equals(persistingHandlerClass)) {
                return persistingHandler;
            }
        }
        return defaultPersistingHandler;
    }

    /**
     * {@inheritDoc}
     */
    public void setDefault(Class<?> persistingHandlerClass) {
        if (persistingHandlerClass != null) {
            for (PersistingHandler persistingHandler : this.persistingHandlers) {
                if (persistingHandler.getClass().equals(persistingHandlerClass)) {
                    defaultPersistingHandler = persistingHandler;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void add(PersistingHandler persistingHandler) {
        if (persistingHandler != null) {
            this.persistingHandlers.add(persistingHandler);
        }
    }
}
