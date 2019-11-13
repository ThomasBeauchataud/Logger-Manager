package com.github.ffcfalcos.logger.handler.persisting;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FilePersistingHandler implements FilePersistingHandlerInterface {

    private String filePath = loadFilePath();

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void persist(String content) {
        try {
            if(filePath != null) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filePath, true));
                bufferedWriter.append("\n");
                bufferedWriter.append(content);
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadFilePath() {
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            return  (String) env.lookup("log-path");
        } catch (Exception e) {
            return null;
        }
    }
}
