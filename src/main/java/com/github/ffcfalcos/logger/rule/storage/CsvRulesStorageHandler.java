package com.github.ffcfalcos.logger.rule.storage;

import com.github.ffcfalcos.logger.rule.Entry;
import com.github.ffcfalcos.logger.rule.Rule;
import com.github.ffcfalcos.logger.util.FilePathService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Managing Rules entities by storing, getting and removing them with a CSV file
 * The CSV file is always accessible and editable while he is not read by the RulesLoader
 */
@SuppressWarnings("unused")
public class CsvRulesStorageHandler implements FileRulesStorageHandler {

    private String filePath;

    /**
     * CsvRulesStorageHandler Constructor
     * Initialize the default file path
     */
    public CsvRulesStorageHandler() {
        this.filePath = System.getProperty("user.dir") + "/csv-rules.csv";
    }

    /**
     * Return all stored rules
     * @return Rule[]
     */
    @Override
    public List<Rule> getRules() {
        List<Rule> rules = new ArrayList<>();
        try {
            if(filePath != null) {
                FilePathService.checkFilePath(filePath);
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

    /**
     * Remove multiple rules
     * @param rules Rule[]
     */
    @Override
    public void removeRules(List<Rule> rules) {
        if(rules != null) {
            List<Rule> actualRules = getRules();
            List<Rule> finalRules = new ArrayList<>();
            for (Rule rule : actualRules) {
                boolean isToSave = true;
                for (Rule ruleTest : rules) {
                    if (rule.equalsTo(ruleTest)) {
                        isToSave = false;
                        break;
                    }
                }
                if(isToSave) {
                    finalRules.add(rule);
                }
            }
            writeRules(finalRules);
        }
    }

    /**
     * Add new rules
     * @param rules Rule[]
     */
    @Override
    public void addRules(List<Rule> rules) {
        if(rules != null) {
            List<Rule> actualRules = getRules();
            actualRules.addAll(rules);
            writeRules(actualRules);
        }
    }

    /**
     * Modify the actual file path and migrate all rule from the previous file in the new file
     * @param filePath String
     */
    public void modifyFilePathAndMigrateRules(String filePath) {
        List<Rule> rules = this.getRules();
        this.filePath = filePath;
        this.writeRules(rules);
    }

    /**
     * Modify the actual file path
     * Be advised that previous rules stored in the previous csv file will be ignored
     * To migrate previous rules in the new file, use modifyFilePath method
     * @param filePath String
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Return the actual file path
     * @return String
     */
    @Override
    public String getFilePath() {
        return filePath;
    }

    /**
     * Write rules in a csv file
     * @param rules Rule[]
     */
    private void writeRules(List<Rule> rules) {
        try {
            FilePathService.checkFilePath(filePath);
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

    /**
     * Create a Rule entity from a string array get by the csv file
     * @param data String[]
     * @return Rule
     */
    private Rule createRule(String[] data) {
        Class<?> persistingHandlerClass;
        Class<?> formatterHandlerClass;
        try {
            persistingHandlerClass = Class.forName(data[3]);
        } catch (Exception e) {
            persistingHandlerClass = null;
        }
        try {
            formatterHandlerClass = Class.forName(data[4]);
        } catch (Exception e) {
            formatterHandlerClass = null;
        }
        return new Rule(data[0], data[1], Entry.valueOf(data[2]), persistingHandlerClass, formatterHandlerClass);
    }

}
