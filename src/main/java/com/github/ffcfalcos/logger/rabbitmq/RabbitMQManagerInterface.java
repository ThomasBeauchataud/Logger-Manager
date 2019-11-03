package com.github.ffcfalcos.logger.rabbitmq;

import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 2.0.0
 * This class permit to send message to a RabbitMQ with the methods sendToRabbit
 * It also create a Thread for each message and validate the JSON message to prevent errors
 */
public interface RabbitMQManagerInterface {

    /**
     * Execute a Thread which send a JSON message to a RabbitMQ
     * @param content String a JSON String
     * @param parameters String[] RabbitMQ parameters
     *      0 - RabbitMQ host
     *      1 - RabbitMQ user
     *      2 - RabbitMQ password
     *      3 - RabbitMQ exchange
     *      4 - RabbitMQ routing key
     */
    void sendToRabbit(String content, List<String> parameters);

}
