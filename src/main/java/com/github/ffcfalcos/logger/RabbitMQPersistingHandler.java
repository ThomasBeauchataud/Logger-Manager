package com.github.ffcfalcos.logger;

import org.json.JSONObject;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Persist a log message to a RabbitMQ with AMQP
 */
@SuppressWarnings("unused")
public class RabbitMQPersistingHandler implements PersistingHandler {

    private String rabbitMQHost;
    private String rabbitMQUser;
    private String rabbitMQPassword;
    private String rabbitMQExchange;
    private String rabbitMQRoutingKey;

    /**
     * RabbitMQPersistingHandler Constructor
     * Set the most common RabbitMQ parameters
     */
    public RabbitMQPersistingHandler() {
        rabbitMQHost = "localhost";
        rabbitMQUser = "guest";
        rabbitMQPassword = "guest";
        rabbitMQExchange = "COMMON";
        rabbitMQRoutingKey = "*";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(String content) {
        try {
            if (content != null && isJSONValid(content)) {
                new Thread(new RabbitMQThread(rabbitMQHost, rabbitMQUser, rabbitMQPassword, rabbitMQExchange, rabbitMQRoutingKey, content)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the actual RabbitMQ host parameter
     *
     * @return String
     */
    public String getRabbitMQHost() {
        return rabbitMQHost;
    }

    /**
     * Set the new RabbitMQ host parameter
     *
     * @param rabbitMQHost String
     */
    public void setRabbitMQHost(String rabbitMQHost) {
        this.rabbitMQHost = rabbitMQHost;
    }

    /**
     * Return the actual RabbitMQ user parameter
     *
     * @return String
     */
    public String getRabbitMQUser() {
        return rabbitMQUser;
    }

    /**
     * Set the new RabbitMQ user parameter
     *
     * @param rabbitMQUser String
     */
    public void setRabbitMQUser(String rabbitMQUser) {
        this.rabbitMQUser = rabbitMQUser;
    }

    /**
     * Return the actual RabbitMQ password parameter
     *
     * @return String
     */
    public String getRabbitMQPassword() {
        return rabbitMQPassword;
    }

    /**
     * Set the new RabbitMQ password parameter
     *
     * @param rabbitMQPassword String
     */
    public void setRabbitMQPassword(String rabbitMQPassword) {
        this.rabbitMQPassword = rabbitMQPassword;
    }

    /**
     * Return the actual RabbitMQ exchange parameter
     *
     * @return String
     */
    public String getRabbitMQExchange() {
        return rabbitMQExchange;
    }

    /**
     * Set the new RabbitMQ exchange parameter
     *
     * @param rabbitMQExchange String
     */
    public void setRabbitMQExchange(String rabbitMQExchange) {
        this.rabbitMQExchange = rabbitMQExchange;
    }

    /**
     * Return the actual RabbitMQ routing-key parameter
     *
     * @return String
     */
    public String getRabbitMQRoutingKey() {
        return rabbitMQRoutingKey;
    }

    /**
     * Set the new RabbitMQ routing-key parameter
     *
     * @param rabbitMQRoutingKey String
     */
    public void setRabbitMQRoutingKey(String rabbitMQRoutingKey) {
        this.rabbitMQRoutingKey = rabbitMQRoutingKey;
    }

    /**
     * Validate a Json string
     *
     * @param test String
     * @return boolean
     */
    private boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
