package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.persistingHandler.PersistingHandlerInterface;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 2.1.0
 * This class permit to log a message by different way and you add you custom persisting handler
 */
@SuppressWarnings("unused")
public interface LoggerInterface {

    /**
     * Add a new PersistingHandler to the logger
     * @param persistingHandler {@link PersistingHandlerInterface}
     */
    void addPersistingHandler(PersistingHandlerInterface persistingHandler);

    /**
     * Log a message with the default method
     * By default, its the {@link com.github.ffcfalcos.logger.persistingHandler.RabbitMQManager}
     * @param message String
     */
    void log(String message);

    /**
     * Log a message with a specific {@link PersistingHandlerInterface}
     * @param message String
     * @param persistingHandlerName String the name of the persisting handler class, if it doesn't exists, the default
     *      persisting handler will be used
     */
    void log(String message, String persistingHandlerName);

}
