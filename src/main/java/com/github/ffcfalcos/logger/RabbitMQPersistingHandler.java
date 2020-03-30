package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.util.FileService;
import com.github.ffcfalcos.logger.util.XmlReader;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONObject;

import java.io.File;

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
        File file = FileService.getConfigFile();
        if(file != null) {
            try {
                rabbitMQHost = XmlReader.getElement(file, "rabbitMQ-host");
                rabbitMQUser = XmlReader.getElement(file, "rabbitMQ-user");
                rabbitMQPassword = XmlReader.getElement(file, "rabbitMQ-password");
                rabbitMQExchange = XmlReader.getElement(file, "rabbitMQ-exchange");
                rabbitMQRoutingKey = XmlReader.getElement(file, "rabbitMQ-routingKey");
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
            if (content != null && isJSONValid(content)) {
                new Thread(() -> send(content)).start();
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

    /**
     * Send the log message to the RabbitMQ on a new thread
     *
     * @param content String
     */
    private void send(String content) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(rabbitMQHost);
            factory.setUsername(rabbitMQUser);
            factory.setPassword(rabbitMQPassword);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicPublish(rabbitMQExchange, rabbitMQRoutingKey, null, content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
