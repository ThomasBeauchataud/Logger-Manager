package com.github.ffcfalcos.logger.handler.persisting;

import com.github.ffcfalcos.logger.util.FilePathService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Persist a log message in a file
 * Folders will be automatically created for file path if needed
 */
@SuppressWarnings("unused")
public class FilePersistingHandler implements PersistingHandler {

    private String filePath;

    /**
     * FilePersistingHandler Constructor
     * Initialize the file path with a default path
     */
    public FilePersistingHandler() {
        filePath = System.getProperty("user.dir") + "/default-log.log";
    }

    /**
     * Persist a string message
     * @param content String
     */
    @Override
    public void persist(String content) {
        try {
            if(filePath != null) {
                FilePathService.checkFilePath(filePath);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filePath, true));
                bufferedWriter.append("\n");
                bufferedWriter.append(content);
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modify the log file path to persist in
     * @param filePath String
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Return the actual file path
     * @return String
     */
    public String getFilePath() {
        return filePath;
    }

}
