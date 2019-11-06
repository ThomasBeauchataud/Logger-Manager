package com.github.ffcfalcos.logger.persistingHandler;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 2.1.0
 * This class send a message to a RabbitMQ by a Thread to not slow the main program
 */
class RabbitMQThread implements Runnable {

    private String host;
    private String user;
    private String password;
    private String exchange;
    private String routingKey;
    private String content;

    /**
     * RabbitMQThread Constructor
     * @param host String RabbitMQ host
     * @param user String RabbitMQ user
     * @param password String RabbitMQ password
     * @param exchange String RabbitMQ exchange name
     * @param routingKey String RabbitMQ exchange routing key
     * @param content String the message
     */
    RabbitMQThread(String host, String user, String password, String exchange, String routingKey, String content) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setUsername(user);
            factory.setPassword(password);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicPublish(exchange, routingKey, null, content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
