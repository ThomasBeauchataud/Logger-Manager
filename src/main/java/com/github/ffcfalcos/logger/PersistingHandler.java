package com.github.ffcfalcos.logger;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Persist a log message
 * This class must not modified to message to persist
 */
public interface PersistingHandler {

    /**
     * Persist a string message
     * @param content String
     */
    void persist(String content);

}
