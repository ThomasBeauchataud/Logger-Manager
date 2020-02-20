package com.github.ffcfalcos.logger.handler.persisting;

import org.json.simple.parser.JSONParser;

@SuppressWarnings("unused")
public class RabbitMQPersistingHandler implements PersistingHandler {

    private String rabbitMQHost;
    private String rabbitMQUser;
    private String rabbitMQPassword;
    private String rabbitMQExchange;
    private String rabbitMQRoutingKey;

    @Override
    public void persist(String content) {
        try {
            if(content != null && isJSONValid(content)) {
                new Thread(new RabbitMQThread(rabbitMQHost, rabbitMQUser, rabbitMQPassword, rabbitMQExchange, rabbitMQRoutingKey, content)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRabbitParameters(String rabbitMQHost, String rabbitMQUser, String rabbitMQPassword, String rabbitMQExchange, String rabbitMQRoutingKey) {
        this.rabbitMQHost = rabbitMQHost;
        this.rabbitMQUser = rabbitMQUser;
        this.rabbitMQPassword = rabbitMQPassword;
        this.rabbitMQExchange = rabbitMQExchange;
        this.rabbitMQRoutingKey = rabbitMQRoutingKey;
    }

    private boolean isJSONValid(String test) {
        try {
            new JSONParser().parse(test);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
