package com.github.ffcfalcos.logger.interceptor;

import com.github.ffcfalcos.logger.Logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@SuppressWarnings("rawtypes")
public @interface TraceAround {

    Class persistingHandlerClass() default void.class;

    Class formatterHandlerClass() default void.class;

}
