package com.github.ffcfalcos.logger.trace;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Thread that load rules from the RuleStorageHandler and inject them in the
 * AbstractTraceableAnnotationHandler rules list
 */
public abstract class AbstractRulesLoader implements Runnable {

    protected List<Rule> rules;
    protected RulesStorageHandler rulesStorageHandler;

    /**
     * AbstractRulesLoader Constructor
     *
     * @param rulesStorageHandler RulesStorageHandler
     */
    public AbstractRulesLoader(RulesStorageHandler rulesStorageHandler) {
        this.rulesStorageHandler = rulesStorageHandler;
        this.rules = new ArrayList<>();
    }

    /**
     * Return the rule list
     *
     * @return Rule[]
     */
    public List<Rule> getRules() {
        return this.rules;
    }

    /**
     * Return the RulesStorageHandler
     *
     * @return RulesStorageHandler
     */
    public RulesStorageHandler getRulesStorageHandler() {
        return rulesStorageHandler;
    }
}
