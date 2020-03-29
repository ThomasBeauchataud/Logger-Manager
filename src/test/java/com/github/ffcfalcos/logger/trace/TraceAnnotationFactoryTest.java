package com.github.ffcfalcos.logger.trace;

import com.github.ffcfalcos.logger.FilePersistingHandler;
import com.github.ffcfalcos.logger.JsonFormatterHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TraceAnnotationFactoryTest {

    @Test
    void createTraceBefore() {
        try {
            Rule rule = new Rule("Main", "main", Entry.BEFORE, FilePersistingHandler.class, JsonFormatterHandler.class, false);
            TraceBefore traceBefore = TraceAnnotationFactory.createTraceBefore(rule);
            assertEquals(traceBefore.context(), rule.getContext());
            assertEquals(traceBefore.persistingHandlerClass(), rule.getPersistingHandlerClass());
            assertEquals(traceBefore.formatterHandlerClass(), rule.getFormatterHandlerClass());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void createTraceAfter() {
        try {
            Rule rule = new Rule("Main", "main", Entry.AFTER, FilePersistingHandler.class, JsonFormatterHandler.class, false);
            TraceAfter traceAfter = TraceAnnotationFactory.createTraceAfter(rule);
            assertEquals(traceAfter.context(), rule.getContext());
            assertEquals(traceAfter.persistingHandlerClass(), rule.getPersistingHandlerClass());
            assertEquals(traceAfter.formatterHandlerClass(), rule.getFormatterHandlerClass());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void createTraceAround() {
        try {
            Rule rule = new Rule("Main", "main", Entry.AROUND, FilePersistingHandler.class, JsonFormatterHandler.class, false);
            TraceAround traceAround = TraceAnnotationFactory.createTraceAround(rule);
            assertEquals(traceAround.context(), rule.getContext());
            assertEquals(traceAround.persistingHandlerClass(), rule.getPersistingHandlerClass());
            assertEquals(traceAround.formatterHandlerClass(), rule.getFormatterHandlerClass());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void createTraceAfterThrowing() {
        try {
            Rule rule = new Rule("Main", "main", Entry.AFTER_THROWING, FilePersistingHandler.class, JsonFormatterHandler.class, false);
            TraceAfterThrowing traceAfterThrowing = TraceAnnotationFactory.createTraceAfterThrowing(rule);
            assertEquals(traceAfterThrowing.context(), rule.getContext());
            assertEquals(traceAfterThrowing.persistingHandlerClass(), rule.getPersistingHandlerClass());
            assertEquals(traceAfterThrowing.formatterHandlerClass(), rule.getFormatterHandlerClass());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}