package com.github.ffcfalcos.logger.handler.persisting;

public class ConsolePersistingHandler implements PersistingHandlerInterface {

    @Override
    public void persist(String content) {
        System.out.println(content);
    }

}
