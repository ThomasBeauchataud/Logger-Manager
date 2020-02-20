package com.github.ffcfalcos.logger.rule.storage;

import com.github.ffcfalcos.logger.rule.Rule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CsvRuleStorageHandler implements RuleStorageHandler {

    private String filePath = System.getProperty("user.dir") + "/csv-rules.csv";

    @Override
    public List<Rule> getRules() {
        List<Rule> rules = new ArrayList<>();
        try {
            if(filePath != null) {
                new File(filePath).createNewFile();
                BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
                String row;
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",");
                    rules.add(createRule(data));
                }
                csvReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rules;
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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private void writeRules(List<Rule> rules) {
        try {
            FileWriter csvWriter = new FileWriter(filePath);
            for (Rule rule : rules) {
                csvWriter.append(String.join(",", rule.toStringList()));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Rule createRule(String[] data) {
        //TODO implements this
        return null;
    }

}
