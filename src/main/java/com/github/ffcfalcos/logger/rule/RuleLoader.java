package com.github.ffcfalcos.logger.rule;

import com.github.ffcfalcos.logger.LoggerInterface;

import java.util.List;

public class RuleLoader implements Runnable {

    private List<Rule> rules;
    private LoggerInterface logger;

    public RuleLoader(List<Rule> rules, LoggerInterface logger) {
        this.rules = rules;
        this.logger = logger;
    }

    @Override
    public void run() {
        rules.clear();
        rules.addAll(logger.getRuleStorageHandler().getRules());
    }

}
