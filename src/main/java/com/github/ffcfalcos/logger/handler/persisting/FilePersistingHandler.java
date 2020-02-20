package com.github.ffcfalcos.logger.handler.persisting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("unused")
public class FilePersistingHandler implements PersistingHandler {

    private String filePath;

    public FilePersistingHandler() {
        filePath = System.getProperty("user.dir") + "/default-log.log";
    }

    @Override
    public void persist(String content) {
        try {
            if(filePath != null) {
                new File(filePath).createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filePath, true));
                bufferedWriter.append("\n");
                bufferedWriter.append(content);
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
