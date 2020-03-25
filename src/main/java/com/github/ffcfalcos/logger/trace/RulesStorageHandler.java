package com.github.ffcfalcos.logger.trace;

import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Managing Rules entities by storing, getting and removing them
 * We have to be able to create, modify and remove rules during the application runtime
 */
@SuppressWarnings("unused")
public interface RulesStorageHandler {

    /**
     * Return all stored rules
     * @return Rule[]
     */
    List<Rule> getRules();

    /**
     * Remove multiple rules
     * @param rules Rule[]
     */
    void removeRules(List<Rule> rules);

    /**
     * Add new rules
     * @param rules Rule[]
     */
    void addRules(List<Rule> rules);

}
