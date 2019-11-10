package com.github.ffcfalcos.logger.persistingHandler;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to persist a message
 */
public interface PersistingHandlerInterface {

    /**
     * Persist a log content
     * @param content String
     */
    void persist(String content);

}
