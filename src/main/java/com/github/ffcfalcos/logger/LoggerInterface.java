package com.github.ffcfalcos.logger;

import java.util.List;

public interface LoggerInterface {

    /**
     * Log a message with the default method
     *      By default, its the console
     * @param message String
     */
    void log(String message);

    /**
     * Log a message with different ways
     * @param message String
     * @param rabbit boolean, true to log with rabbitMQ (if rabbitMQ parameters are declared)
     * @param file boolean, true to log with file (if filePath is declared)
     * @param console boolean, true to log with the console
     */
    void log(String message, boolean rabbit, boolean file, boolean console);

    /**
     * Set the filePath of logs
     * @param filePath String, absolute filePath
     */
    void setFilePath(String filePath);

    /**
     * Set RabbitMQ parameters as follow
     * @param parameters
     * String[] RabbitMQ parameters
     *              0 - RabbitMQ host
     *              1 - RabbitMQ user
     *              2 - RabbitMQ password
     *              3 - RabbitMQ exchange
     *              4 - RabbitMQ routing key
     */
    void setRabbitParameters(List<String> parameters);

    /**
     * Set the default log type
     * @param defaultCode int
     *              0 - console
     *              1 - rabbitMQ
     *              2 - file
     *              else: no change
     */
    void setDefault(int defaultCode);

}
