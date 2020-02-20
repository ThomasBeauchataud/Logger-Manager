package com.github.ffcfalcos.logger.rule.storage;

import com.github.ffcfalcos.logger.rule.Rule;

import java.util.List;

@SuppressWarnings("unused")
public interface RuleStorageHandler {

    List<Rule> getRules();

    void removeRules(List<Rule>rules);

    void addRules(List<Rule> rules);

}
