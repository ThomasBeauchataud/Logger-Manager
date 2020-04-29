package com.github.ffcfalcos.logger.trace;

import com.github.ffcfalcos.logger.util.FileService;
import com.github.ffcfalcos.logger.util.XmlReader;

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
        this.filePath = System.getProperty("user.dir") + "/rules.csv";
        File file = FileService.getConfigFile();
        if(file != null) {
            if(XmlReader.getElement(file, "csv-rules-storage-handler-path") != null) {
                this.filePath = System.getProperty("user.dir") + XmlReader.getElement(file, "csv-rules-storage-handler-path");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Rule> getRules() {
        List<Rule> rules = new ArrayList<>();
        try {
            if (filePath != null) {
                FileService.createFilePath(filePath);
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
     * {@inheritDoc}
     */
    @Override
    public void removeRules(List<Rule> rules) {
        if (rules != null) {
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
                if (isToSave) {
                    finalRules.add(rule);
                }
            }
            writeRules(finalRules);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRules(List<Rule> rules) {
        if (rules != null) {
            List<Rule> actualRules = getRules();
            actualRules.addAll(rules);
            writeRules(actualRules);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFilePath(String filePath, boolean migration) {
        if (migration) {
            List<Rule> rules = this.getRules();
            this.filePath = filePath;
            this.writeRules(rules);
        } else {
            this.filePath = filePath;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilePath() {
        return filePath;
    }

    /**
     * Write rules in a csv file
     *
     * @param rules Rule[]
     */
    private void writeRules(List<Rule> rules) {
        try {
            FileService.createFilePath(filePath);
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
     *
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
        return new Rule(data[0], data[1], Entry.valueOf(data[2]), persistingHandlerClass, formatterHandlerClass, Boolean.parseBoolean(data[5]));
    }

}
