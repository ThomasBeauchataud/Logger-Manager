package com.github.ffcfalcos.logger.handler.persisting;

@SuppressWarnings("unused")
interface RabbitMQPersistingHandlerInterface extends PersistingHandlerInterface {

    void setRabbitMQHost(String rabbitMQHost);

    void setRabbitMQUser(String rabbitMQUser);

    void setRabbitMQPassword(String rabbitMQPassword);

    void setRabbitMQExchange(String rabbitMQExchange);

    void setRabbitMQRoutingKey(String rabbitMQRoutingKey);
    
}
