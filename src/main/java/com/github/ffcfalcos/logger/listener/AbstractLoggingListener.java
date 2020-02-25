package com.github.ffcfalcos.logger.listener;

import com.github.ffcfalcos.logger.Logger;
import com.github.ffcfalcos.logger.LoggerInterface;
import com.github.ffcfalcos.logger.collector.LogContent;
import com.github.ffcfalcos.logger.handler.formatter.FormatterHandler;
import com.github.ffcfalcos.logger.handler.persisting.PersistingHandler;

import java.util.EventObject;

@SuppressWarnings("unused")
public abstract class AbstractLoggingListener {

    protected boolean active;
    protected LoggerInterface logger;
    protected PersistingHandler persistingHandler;
    protected FormatterHandler formatterHandler;

    protected AbstractLoggingListener(LoggerInterface logger) {
        this.logger = logger;
        active = true;
    }

    protected AbstractLoggingListener() {
        this.logger = Logger.getInstance();
        active = true;
    }

    public void enable() {
        this.active = true;
    }

    public void disable() {
        this.active = false;
    }

    protected void handleListener(EventObject eventObject) {
        if(active) {
            LogContent logContent = new LogContent("listener");
            logContent.put("event-object", eventObject.toString());
            logger.log(logContent.close(), persistingHandler.getClass(), formatterHandler.getClass());
        }
    }

    protected void handleListener(EventObject eventObject, LogContent logContent) {
        logContent.put("event-object", eventObject.toString());
        logger.log(logContent.close(), persistingHandler.getClass(), formatterHandler.getClass());
    }

}
