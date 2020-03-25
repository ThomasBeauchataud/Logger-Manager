package com.github.ffcfalcos.logger.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Annotation to place on a field to never log it
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LogIgnored {
}
