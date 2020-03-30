package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.util.FileService;
import com.github.ffcfalcos.logger.util.XmlReader;

import java.io.File;
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
        File file = FileService.getConfigFile();
        if(file != null) {
            try {
                for(String persistingHandlerClassName : XmlReader.getElements(file, "persisting-handler")) {
                    Class<?> persistingHandlerClass = Class.forName(persistingHandlerClassName);
                    add((PersistingHandler) persistingHandlerClass.getConstructor().newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
