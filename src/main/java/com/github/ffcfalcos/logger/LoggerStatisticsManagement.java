package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.util.FilePathService;

import java.io.BufferedReader;
import java.io.FileReader;
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
     *
     * @param active boolean
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Update the historic of handlers utilisation
     *
     * @param persistingHandlerClass Class
     * @param formatterHandlerClass  Class
     */
    public void update(Class<?> persistingHandlerClass, Class<?> formatterHandlerClass) {
        if (active) {
            try {
                FileWriter csvWriter = new FileWriter(filePath, true);
                csvWriter.append(Long.toString(new Date().getTime()));
                csvWriter.append(",");
                csvWriter.append(persistingHandlerClass.getName());
                csvWriter.append(",");
                csvWriter.append(formatterHandlerClass.getName());
                csvWriter.append("\n");
                csvWriter.flush();
                csvWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set the new file path for the file storing statistics
     * Set migration to true if you want to migrate statistics of the actual file into the new one
     *
     * @param filePath  String
     * @param migration boolean
     */
    public void setFilePath(String filePath, boolean migration) {
        FilePathService.checkFilePath(filePath);
        if (migration) {
            try {
                FileWriter csvWriter = new FileWriter(filePath);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(this.filePath));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    csvWriter.append(line);
                    csvWriter.append("\n");
                }
                bufferedReader.close();
                csvWriter.flush();
                csvWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.filePath = filePath;
    }
}
