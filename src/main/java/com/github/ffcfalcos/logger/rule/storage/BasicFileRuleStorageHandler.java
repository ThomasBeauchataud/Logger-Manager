package com.github.ffcfalcos.logger.rule.storage;

import com.github.ffcfalcos.logger.rule.Rule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class BasicFileRuleStorageHandler implements RuleStorageHandler {

    private static final String filePath = System.getProperty("user.dir") + "/basic-rules";

    @Override
    public List<Rule> getRules() {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Rule> rules = (List<Rule>)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return rules;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void removeRules(List<Rule> rules) {
        List<Rule> actualRules = getRules();
        for(Rule rule : actualRules) {
            for(Rule ruleTest : rules) {
                if(rule.equals(ruleTest)) {
                    actualRules.remove(ruleTest);
                }
            }
        }
        writeRules(actualRules);
    }

    @Override
    public void addRules(List<Rule> rules) {
        List<Rule> actualRules = getRules();
        actualRules.addAll(rules);
        writeRules(actualRules);
    }

    private void writeRules(List<Rule> rules) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(rules);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
