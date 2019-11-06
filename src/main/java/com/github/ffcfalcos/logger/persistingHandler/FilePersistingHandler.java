package com.github.ffcfalcos.logger.persistingHandler;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 2.1.0
 * This class permit to persist a message in a file
 * If they are set, all parameters are automatically load :
 *      log-path
 */
public class FilePersistingHandler implements PersistingHandlerInterface {

    private String filePath;

    /**
     * FilePersistingHandler Constructor
     */
    public FilePersistingHandler() {
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            filePath = (String) env.lookup("log-path");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Persist a log content
     * @param content String
     */
    @Override
    public void persist(String content) {
        try {
            FileWriter fw = new FileWriter(this.filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
