package com.github.ffcfalcos.logger.trace;

import java.lang.annotation.Annotation;

@SuppressWarnings("Duplicates")
class TraceAnnotationFactory {

    public static TraceBefore createTraceBefore(Rule rule) {
        return new TraceBefore() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return TraceBefore.class;
            }

            @Override
            public Class<?> persistingHandlerClass() {
                return rule.getPersistingHandlerClass();
            }

            @Override
            public Class<?> formatterHandlerClass() {
                return rule.getFormatterHandlerClass();
            }

            @Override
            public boolean context() {
                return rule.getContext();
            }
        };
    }

    public static TraceAfter createTraceAfter(Rule rule) {
        return new TraceAfter() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return TraceBefore.class;
            }

            @Override
            public Class<?> persistingHandlerClass() {
                return rule.getPersistingHandlerClass();
            }

            @Override
            public Class<?> formatterHandlerClass() {
                return rule.getFormatterHandlerClass();
            }

            @Override
            public boolean context() {
                return rule.getContext();
            }
        };
    }

    public static TraceAround createTraceAround(Rule rule) {
        return new TraceAround() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return TraceBefore.class;
            }

            @Override
            public Class<?> persistingHandlerClass() {
                return rule.getPersistingHandlerClass();
            }

            @Override
            public Class<?> formatterHandlerClass() {
                return rule.getFormatterHandlerClass();
            }

            @Override
            public boolean context() {
                return rule.getContext();
            }
        };
    }

    public static TraceAfterThrowing createTraceAfterThrowing(Rule rule) {
        return new TraceAfterThrowing() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return TraceBefore.class;
            }

            @Override
            public Class<?> persistingHandlerClass() {
                return rule.getPersistingHandlerClass();
            }

            @Override
            public Class<?> formatterHandlerClass() {
                return rule.getFormatterHandlerClass();
            }

            @Override
            public boolean context() {
                return rule.getContext();
            }
        };
    }

}
