package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.rabbitmq.RabbitMQManager;
import com.github.ffcfalcos.rabbitmq.RabbitMQManagerInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * At the construction of the logger, we try to find env-entry parameters
 * If they are set, all parameters are automatically load
 * Parameters for Rabbit:
 *                  rabbitMQ-host
 *                  rabbitMQ-user
 *                  rabbitMQ-password
 *                  rabbitMQ-exchange-logger
 *                  rabbitMQ-routingKey-logger
 * Parameters for File:
 *                  log-path
 */
public class Logger implements LoggerInterface {

    private final RabbitMQManagerInterface rabbitMQManager = new RabbitMQManager();
    private String filePath;
    private List<String> rabbitMQParameters;
    private int defaultLogger = 0;

    public Logger() {
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            rabbitMQParameters = Arrays.asList(
                    (String) env.lookup("rabbitMQ-host"),
                    (String) env.lookup("rabbitMQ-user"),
                    (String) env.lookup("rabbitMQ-password"),
                    (String) env.lookup("rabbitMQ-exchange-logger"),
                    (String) env.lookup("rabbitMQ-routingKey-logger"));
            this.filePath = (String) env.lookup("log-path");
            this.defaultLogger = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(String content) {
        if(defaultLogger == 0) {
            System.out.println(content);
        }
        if(defaultLogger == 1 && rabbitMQParameters != null) {
            rabbitMQManager.sendToRabbit(content, rabbitMQParameters);
        }
        if(defaultLogger == 2 && filePath != null) {
            this.logFile(content);
        }
    }

    @Override
    public void log(String message, boolean rabbit, boolean file, boolean console) {
        if(rabbit && rabbitMQParameters != null) {
            rabbitMQManager.sendToRabbit(message, rabbitMQParameters);
        }
        if(file && filePath != null) {
            this.logFile(message);
        }
        if(console) {
            System.out.println(message);
        }
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void setRabbitParameters(List<String> parameters) {
        this.rabbitMQParameters = parameters;
    }

    @Override
    public void setDefault(int defaultCode) {
        if(defaultCode == 0 || defaultCode == 1 || defaultCode == 2) {
            this.defaultLogger = defaultCode;
        }
    }

    private void logFile(String content) {
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
