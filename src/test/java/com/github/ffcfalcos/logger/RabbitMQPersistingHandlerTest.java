package com.github.ffcfalcos.logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMQPersistingHandlerTest {

    @Test
    void persist() {
        try {
            RabbitMQPersistingHandler rabbitMQPersistingHandler = new RabbitMQPersistingHandler();
            rabbitMQPersistingHandler.setRabbitMQHost("localhost");
            rabbitMQPersistingHandler.setRabbitMQPassword("guest");
            rabbitMQPersistingHandler.setRabbitMQUser("guest");
            rabbitMQPersistingHandler.setRabbitMQExchange("test");
            rabbitMQPersistingHandler.setRabbitMQRoutingKey("*");
            rabbitMQPersistingHandler.persist("Here is a test message");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}