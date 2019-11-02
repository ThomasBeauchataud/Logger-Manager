package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.rabbitmq.RabbitMQManager;
import com.github.ffcfalcos.rabbitmq.RabbitMQManagerInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Logger implements LoggerInterface {

    private final RabbitMQManagerInterface rabbitMQManager = new RabbitMQManager();
    private String filePath;
    private List<String> rabbitMQParameters;
    private int defaultLogger = 0;

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
