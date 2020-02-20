package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.handler.persisting.PersistingHandler;

public class PersistingHandlerTest {

    public void run(PersistingHandler persistingHandler) {
        try {
            persistingHandler.persist("hello");
        } catch (Exception e) {
            System.out.println(persistingHandler.getClass().getSimpleName() + " failed the persist test");
        }
        try {
            persistingHandler.persist(null);
        } catch (Exception e) {
            System.out.println(persistingHandler.getClass().getSimpleName() + " failed the persist null test");
        }
        try {
            persistingHandler.persist("");
        } catch (Exception e) {
            System.out.println(persistingHandler.getClass().getSimpleName() + " failed the persist empty string test");
        }
    }

}
