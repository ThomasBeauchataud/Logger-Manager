package com.github.ffcfalcos.logger.statistic;

import java.io.FileWriter;
import java.util.Date;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Statistics Management component which save a small historic of handlers utilisation
 */
@SuppressWarnings("unused")
public class LoggerStatisticsManagement {

    private String filePath;
    private boolean active;

    /**
     * LoggerStatisticsManagement Constructor
     */
    public LoggerStatisticsManagement() {
        active = false;
        filePath = System.getProperty("user.dir") + "/csv-stats.csv";
    }

    /**
     * Set if the statistics component is active or not
     * @param active boolean
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Update the historic of handlers utilisation
     * @param persistingHandlerClass Class
     * @param formatterHandlerClass Class
     */
    public void update(Class<?> persistingHandlerClass, Class<?> formatterHandlerClass) {
        if(active) {
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

}
