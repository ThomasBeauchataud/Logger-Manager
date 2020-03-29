package com.github.ffcfalcos.logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Send a message to a RabbitMQ server threw a AMQP protocol
 * Messages are sent of different thread to not impact application performances
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
     *
     * @param host       String
     * @param user       String
     * @param password   String
     * @param exchange   String
     * @param routingKey String
     * @param content    String assuming it is a valid Json format
     */
    RabbitMQThread(String host, String user, String password, String exchange, String routingKey, String content) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.content = content;
    }

    /**
     * Run the thread and send the message
     */
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
