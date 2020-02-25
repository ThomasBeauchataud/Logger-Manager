package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.collector.LogContent;
import com.github.ffcfalcos.logger.collector.Severity;
import com.github.ffcfalcos.logger.handler.formatter.FormatterHandler;

public class FormatterHandlerTest {

    public void run(FormatterHandler formatterHandler) {
        try {
            formatterHandler.format(null);
        } catch (Exception e) {
            System.err.println(formatterHandler.getClass().getSimpleName() + " failed the format null map test");
        }
        try {
            formatterHandler.format("hello", null);
        } catch (Exception e) {
            System.err.println(formatterHandler.getClass().getSimpleName() + " failed the format null severity test");
        }
        try {
            formatterHandler.format(null, Severity.DEBUG);
        } catch (Exception e) {
            System.err.println(formatterHandler.getClass().getSimpleName() + " failed the format null string test");
        }
        try {
            formatterHandler.format("", Severity.DEBUG);
        } catch (Exception e) {
            System.err.println(formatterHandler.getClass().getSimpleName() + " failed the format empty string test");
        }
        try {
            formatterHandler.format(new LogContent("test"));
        } catch (Exception e) {
            System.err.println(formatterHandler.getClass().getSimpleName() + " failed the format empty map test");
        }
    }

}
