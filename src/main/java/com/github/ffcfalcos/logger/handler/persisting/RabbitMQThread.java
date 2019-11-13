package com.github.ffcfalcos.logger.handler.persisting;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

class RabbitMQThread implements Runnable {

    private String host;
    private String user;
    private String password;
    private String exchange;
    private String routingKey;
    private String content;

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
