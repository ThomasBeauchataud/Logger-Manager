package com.github.ffcfalcos.logger.rabbitmq;

import org.json.simple.parser.JSONParser;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 03.11.2019
 * @version 2.0.0
 * This class permit to send message to a RabbitMQ with the methods sendToRabbit
 * It also create a Thread for each message and validate the JSON message to prevent errors
 */
@Default
@ApplicationScoped
class RabbitMQManager implements RabbitMQManagerInterface {

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
    @Override
    public void sendToRabbit(String content, List<String> parameters) {
        if(content != null && isJSONValid(content)) {
            try {
                new Thread(new RabbitMQThread(
                        parameters.get(0),
                        parameters.get(1),
                        parameters.get(2),
                        parameters.get(3),
                        parameters.get(4),
                        content
                )).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Validate a JSON String
     * @param test String
     * @return boolean
     */
    private boolean isJSONValid(String test) {
        try {
            new JSONParser().parse(test);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
