package com.github.ffcfalcos.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Annotation to place on a method to be able to trace it if wanted during the application runtime
 * Do not place the annotation on a static method !
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Traceable {
}
