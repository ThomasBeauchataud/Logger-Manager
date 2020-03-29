package com.github.ffcfalcos.logger.trace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Annotation to place on a method to trace it after throwing an exception
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TraceAfterThrowing {

    /**
     * The PersistingHandlerClass to use to trace the method execution
     *
     * @return Class
     */
    Class<?> persistingHandlerClass() default void.class;

    /**
     * The FormatterHandlerClass to use to trace the method execution
     *
     * @return Class
     */
    Class<?> formatterHandlerClass() default void.class;

    /**
     * Define if the context of the invoked method has to be logged or not
     * Make sur that your class has the JsonSerializable annotation if you set it to true
     *
     * @return boolean
     */
    boolean context() default false;

}
