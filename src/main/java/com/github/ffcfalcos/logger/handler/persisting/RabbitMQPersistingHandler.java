package com.github.ffcfalcos.logger.handler.persisting;

import org.json.simple.parser.JSONParser;

import javax.naming.Context;
import javax.naming.InitialContext;

public class RabbitMQPersistingHandler implements RabbitMQPersistingHandlerInterface {

    private String rabbitMQHost = loadHost();
    private String rabbitMQUser = loadUser();
    private String rabbitMQPassword = loadPassword();
    private String rabbitMQExchange = loadExchange();
    private String rabbitMQRoutingKey = loadRoutingKey();

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

    @Override
    public void setRabbitMQHost(String rabbitMQHost) {
        this.rabbitMQHost = rabbitMQHost;
    }

    @Override
    public void setRabbitMQUser(String rabbitMQUser) {
        this.rabbitMQUser = rabbitMQUser;
    }

    @Override
    public void setRabbitMQPassword(String rabbitMQPassword) {
        this.rabbitMQPassword = rabbitMQPassword;
    }

    @Override
    public void setRabbitMQExchange(String rabbitMQExchange) {
        this.rabbitMQExchange = rabbitMQExchange;
    }

    @Override
    public void setRabbitMQRoutingKey(String rabbitMQRoutingKey) {
        this.rabbitMQRoutingKey = rabbitMQRoutingKey;
    }

    private String loadHost() {
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            return (String) env.lookup("rabbitMQ-host");
        } catch (Exception e) {
            return null;
        }
    }

    private String loadUser() {
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            return (String) env.lookup("rabbitMQ-user");
        } catch (Exception e) {
            return null;
        }
    }

    private String loadPassword() {
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            return (String) env.lookup("rabbitMQ-password");
        } catch (Exception e) {
            return null;
        }
    }

    private String loadExchange() {
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            return (String) env.lookup("rabbitMQ-exchange-logger");
        } catch (Exception e) {
            return null;
        }
    }

    private String loadRoutingKey() {
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            return (String) env.lookup("rabbitMQ-routingKey-logger");
        } catch (Exception e) {
            return null;
        }
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
