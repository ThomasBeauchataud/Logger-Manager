package com.github.ffcfalcos.logger;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Provide T Handlers
 */
public interface HandlerProvider<T> {

    /**
     * Return a Handler associated to the class in parameter
     * Return the default Handler if the one in parameter doesn't exists
     *
     * @param handlerClass Class | null to get the default Handler
     * @return T
     */
    T get(Class<?> handlerClass);

    /**
     * Change the default Handler
     *
     * @param handlerClass Class
     */
    void setDefault(Class<?> handlerClass);

    /**
     * Add a new Handler to the Provider
     *
     * @param handler T
     */
    void add(T handler);

}
