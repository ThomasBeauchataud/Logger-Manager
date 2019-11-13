package com.github.ffcfalcos.logger.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TraceBefore {

    String persistingHandlerName() default "default";

    String formatterHandlerName() default "default";

}
