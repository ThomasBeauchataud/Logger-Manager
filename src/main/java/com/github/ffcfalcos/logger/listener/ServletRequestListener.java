package com.github.ffcfalcos.logger.listener;

import com.github.ffcfalcos.logger.LoggerInterface;

import javax.servlet.ServletRequestEvent;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletRequestListener extends AbstractLoggingListener implements javax.servlet.ServletRequestListener {

    public ServletRequestListener(LoggerInterface logger) {
        super(logger);
        super.disable();
    }

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        super.handleListener(event);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        super.handleListener(event);
    }

}
