package com.github.ffcfalcos.logger;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Persist a log message with the out system
 */
public class SystemOutPersistingHandler implements PersistingHandler {

    /**
     * Persist a string message
     * @param content String
     */
    @Override
    public void persist(String content) {
        System.out.println(content);
    }

}
