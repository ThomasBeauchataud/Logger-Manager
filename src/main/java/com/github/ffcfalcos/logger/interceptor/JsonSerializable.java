package com.github.ffcfalcos.logger.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Annotation to place on a class if it can be serialized into json to be logged
 * You can use the LogIgnored annotation on some fields if you want to hide them
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JsonSerializable {
}
