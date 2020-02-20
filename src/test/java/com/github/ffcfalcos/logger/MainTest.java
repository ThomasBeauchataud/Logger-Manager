package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.handler.formatter.JsonFormatterHandler;
import com.github.ffcfalcos.logger.handler.formatter.StringFormatterHandler;
import com.github.ffcfalcos.logger.handler.persisting.ConsolePersistingHandler;
import com.github.ffcfalcos.logger.handler.persisting.FilePersistingHandler;
import com.github.ffcfalcos.logger.handler.persisting.RabbitMQPersistingHandler;

public class MainTest {

    public static void main(String[] args) {
        FormatterHandlerTest formatterHandlerTest = new FormatterHandlerTest();
        formatterHandlerTest.run(new JsonFormatterHandler());
        formatterHandlerTest.run(new StringFormatterHandler());
        PersistingHandlerTest persistingHandlerTest = new PersistingHandlerTest();
        persistingHandlerTest.run(new FilePersistingHandler());
        persistingHandlerTest.run(new ConsolePersistingHandler());
        persistingHandlerTest.run(new RabbitMQPersistingHandler());
    }

}
