package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.persistingHandler.FilePersistingHandler;
import com.github.ffcfalcos.logger.persistingHandler.PersistingHandlerInterface;
import com.github.ffcfalcos.logger.persistingHandler.RabbitMQManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to log a message by different way and you add you custom persisting handler
 */
@Default
@ApplicationScoped
class Logger implements LoggerInterface {

    private PersistingHandlerInterface defaultPersistingHandler;
    private List<PersistingHandlerInterface> persistingHandlerList = new ArrayList<>();

    @PostConstruct
    public void init() {
        defaultPersistingHandler = new RabbitMQManager();
        persistingHandlerList.add(new FilePersistingHandler());
        persistingHandlerList.add(defaultPersistingHandler);
    }

    /**
     * Add a new PersistingHandler to the logger
     * @param persistingHandler {@link PersistingHandlerInterface}
     */
    @Override
    public void addPersistingHandler(PersistingHandlerInterface persistingHandler) {
        persistingHandlerList.add(persistingHandler);
    }

    /**
     * Log a message with the default method
     * By default, its the {@link com.github.ffcfalcos.logger.persistingHandler.RabbitMQManager}
     * @param content String
     */
    @Override
    public void log(String content) {
        defaultPersistingHandler.persist(content);
    }

    /**
     * Log a message with a specific {@link PersistingHandlerInterface}
     * @param message String
     * @param persistingHandlerName String the name of the persisting handler class, if it doesn't exists, the default
     *      persisting handler will be used
     */
    @Override
    public void log(String message, String persistingHandlerName) {
        PersistingHandlerInterface persistingHandler = getPersistingHandlerByName(persistingHandlerName);
        persistingHandler.persist(message);
    }

    /**
     * Return a {@link PersistingHandlerInterface} identified by his name
     * @param persistingHandlerName String
     * @return {@link PersistingHandlerInterface}
     */
    private PersistingHandlerInterface getPersistingHandlerByName(String persistingHandlerName) {
        for(PersistingHandlerInterface persistingHandler : persistingHandlerList) {
            if(persistingHandler.getClass().getName().equals(persistingHandlerName)) {
                return persistingHandler;
            }
        }
        return defaultPersistingHandler;
    }

}
