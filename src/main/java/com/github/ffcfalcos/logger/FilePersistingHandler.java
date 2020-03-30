package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.util.FileService;
import com.github.ffcfalcos.logger.util.XmlReader;

import java.io.BufferedWriter;
import java.io.File;
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
        File file = FileService.getConfigFile();
        if(file != null) {
            try {
                this.filePath = System.getProperty("user.dir") + XmlReader.getElement(file, "file-persisting-handler-path");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(String content) {
        try {
            if (filePath != null) {
                FileService.createFilePath(filePath);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filePath, true));
                bufferedWriter.append(content);
                bufferedWriter.append("\n");
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modify the log file path to persist in
     *
     * @param filePath String
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Return the actual file path
     *
     * @return String
     */
    public String getFilePath() {
        return filePath;
    }

}
