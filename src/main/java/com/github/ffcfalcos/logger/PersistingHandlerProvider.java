package com.github.ffcfalcos.logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Provide Persisting Handlers
 */
@SuppressWarnings("unused")
public class PersistingHandlerProvider {

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
     * Return a PersistingHandler associated to the class in parameter
     * Return the default PersistingHandler if the one in parameter doesn't exists
     * @param persistingHandlerClass Class | null to get the default PersistingHandler
     * @return PersistingHandler
     */
    public PersistingHandler get(Class<?> persistingHandlerClass) {
        if(persistingHandlerClass == null) { return defaultPersistingHandler; }
        for(PersistingHandler persistingHandler : this.persistingHandlers) {
            if(persistingHandler.getClass().equals(persistingHandlerClass)) {
                return persistingHandler;
            }
        }
        return defaultPersistingHandler;
    }

    /**
     * Change the default PersistingHandler
     * @param persistingHandlerClass Class
     */
    public void setDefault(Class<?> persistingHandlerClass) {
        if(persistingHandlerClass != null) {
            for(PersistingHandler persistingHandler : this.persistingHandlers) {
                if(persistingHandler.getClass().equals(persistingHandlerClass)) {
                    defaultPersistingHandler = persistingHandler;
                }
            }
        }
    }

    /**
     * Add a new PersistingHandler to the provider
     * @param persistingHandler PersistingHandler
     */
    public void add(PersistingHandler persistingHandler) {
        if(persistingHandler != null) {
            this.persistingHandlers.add(persistingHandler);
        }
    }
}
