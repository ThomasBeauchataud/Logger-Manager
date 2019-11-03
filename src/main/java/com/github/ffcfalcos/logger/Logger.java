package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.rabbitmq.RabbitMQManager;
import com.github.ffcfalcos.rabbitmq.RabbitMQManagerInterface;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 1.1.0
 * This class permit to log with different way a message
 * With @PostConstructed method we try to find some env-entry parameters
 * If they are set, all parameters are automatically load
 * Parameters for Rabbit:
 *      rabbitMQ-host
 *      rabbitMQ-user
 *      rabbitMQ-password
 *      rabbitMQ-exchange-logger
 *      rabbitMQ-routingKey-logger
 * Parameters for File:
 *      log-path
 */
@Default
@ApplicationScoped
public class Logger implements LoggerInterface {

    private final RabbitMQManagerInterface rabbitMQManager = new RabbitMQManager();
    private String filePath;
    private List<String> rabbitMQParameters;
    private int defaultLogger = 0;

    /**
     * Here we try load some env-entry parameters to initialize parameters
     * Parameters for Rabbit:
     *      rabbitMQ-host
     *      rabbitMQ-user
     *      rabbitMQ-password
     *      rabbitMQ-exchange-logger
     *      rabbitMQ-routingKey-logger
     * Parameters for File:
            log-path
     */
    @PostConstruct
    public void init() {
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

    /**
     * Log a message with the default method
     * By default, its the console
     * @param content String
     */
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

    /**
     * Log a message with different ways
     * @param message String
     * @param rabbit boolean, true to log with rabbitMQ (if rabbitMQ parameters are declared)
     * @param file boolean, true to log with file (if filePath is declared)
     * @param console boolean, true to log with the console
     */
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

    /**
     * Set the filePath of logs
     * @param filePath String, absolute filePath
     */
    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Set RabbitMQ parameters as follow
     * @param parameters
     * String[] RabbitMQ parameters
     *      0 - RabbitMQ host
     *      1 - RabbitMQ user
     *      2 - RabbitMQ password
     *      3 - RabbitMQ exchange
     *      4 - RabbitMQ exchange routing key
     */
    @Override
    public void setRabbitParameters(List<String> parameters) {
        this.rabbitMQParameters = parameters;
    }

    /**
     * Set the default log type
     * @param defaultCode int
     *      0 - console
     *      1 - rabbitMQ
     *      2 - file
     *      else: no change
     */
    @Override
    public void setDefault(int defaultCode) {
        if(defaultCode == 0 || defaultCode == 1 || defaultCode == 2) {
            this.defaultLogger = defaultCode;
        }
    }

    /**
     * Log a message on file defined with parameters
     * @param content String
     */
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
