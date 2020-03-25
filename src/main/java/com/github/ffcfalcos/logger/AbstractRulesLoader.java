package com.github.ffcfalcos.logger;

import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Thread that load rules from the RuleStorageHandler and inject them in the
 *      AbstractTraceableAnnotationHandler rules list
 */
public abstract class AbstractRulesLoader implements Runnable {

    protected List<Rule> rules;
    protected RulesStorageHandler rulesStorageHandler;

    /**
     * AbstractRulesLoader Constructor
     * @param rulesStorageHandler RulesStorageHandler
     */
    public AbstractRulesLoader(RulesStorageHandler rulesStorageHandler) {
        this.rulesStorageHandler = rulesStorageHandler;
    }

    /**
     * Set the Rule list
     * @param rules Rule[]
     */
    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    /**
     * Return the RulesStorageHandler
     * @return RulesStorageHandler
     */
    public RulesStorageHandler getRulesStorageHandler() {
        return rulesStorageHandler;
    }
}
