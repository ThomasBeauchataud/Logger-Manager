package com.github.ffcfalcos.logger.persistingHandler;

import org.json.simple.parser.JSONParser;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 3.0.0
 * This class permit to send message to a RabbitMQ
 * It also create a Thread for each message and validate the JSON message to prevent errors
 * If they are set, all parameters are automatically load :
 *      rabbitMQ-host
 *      rabbitMQ-user
 *      rabbitMQ-password
 *      rabbitMQ-exchange-logger
 *      rabbitMQ-routingKey-logger
 */
public class RabbitMQManager implements PersistingHandlerInterface {

    private String rabbitMQHost;
    private String rabbitMQUser;
    private String rabbitMQPassword;
    private String rabbitMQExchange;
    private String rabbitMQRoutingKey;

    /**
     * RabbitMQManager Constructor
     */
    public RabbitMQManager() {
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            rabbitMQHost = (String) env.lookup("rabbitMQ-host");
            rabbitMQUser = (String) env.lookup("rabbitMQ-user");
            rabbitMQPassword = (String) env.lookup("rabbitMQ-password");
            rabbitMQExchange = (String) env.lookup("rabbitMQ-exchange-logger");
            rabbitMQRoutingKey = (String) env.lookup("rabbitMQ-routingKey-logger");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute a Thread which send a JSON message to a RabbitMQ
     * @param content String a JSON String
     */
    @Override
    public void persist(String content) {
        if(content != null && isJSONValid(content)) {
            try {
                new Thread(new RabbitMQThread(rabbitMQHost, rabbitMQUser, rabbitMQPassword, rabbitMQExchange, rabbitMQRoutingKey, content)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Validate a JSON String
     * @param test String
     * @return boolean
     */
    private boolean isJSONValid(String test) {
        try {
            new JSONParser().parse(test);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
