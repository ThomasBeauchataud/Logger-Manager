package com.github.ffcfalcos.logger.rule;

import com.github.ffcfalcos.logger.rule.storage.RuleStorageHandler;

import java.util.List;

class RuleLoader implements Runnable {

    private List<Rule> rules;
    private RuleStorageHandler ruleStorageHandler;

    public RuleLoader(List<Rule> rules, RuleStorageHandler ruleStorageHandler) {
        this.rules = rules;
        this.ruleStorageHandler = ruleStorageHandler;
    }

    @Override
    public void run() {
        rules.clear();
        rules.addAll(ruleStorageHandler.getRules());
    }

}
