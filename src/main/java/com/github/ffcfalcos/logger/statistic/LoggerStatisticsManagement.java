package com.github.ffcfalcos.logger.statistic;

import java.io.FileWriter;
import java.util.Date;

@SuppressWarnings("rawtypes")
public class LoggerStatisticsManagement {

    private String filePath;

    public LoggerStatisticsManagement() {
        filePath = System.getProperty("user.dir") + "/csv-stats.csv";
    }

    public void update(Class persistingHandlerClass, Class formatterHandlerClass) {
        try {
            FileWriter csvWriter = new FileWriter(filePath);
            csvWriter.append(Float.toString(new Date().getTime()));
            csvWriter.append(",");
            csvWriter.append(persistingHandlerClass.getSimpleName());
            csvWriter.append(",");
            csvWriter.append(formatterHandlerClass.getSimpleName());
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
