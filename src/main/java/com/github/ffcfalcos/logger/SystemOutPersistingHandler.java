package com.github.ffcfalcos.logger;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Persist a log message with the out system
 */
public class SystemOutPersistingHandler implements PersistingHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(String content) {
        System.out.println(content);
    }

}
