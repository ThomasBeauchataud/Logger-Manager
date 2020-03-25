package com.github.ffcfalcos.logger.rule;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Entry type to know when trigger the Rule
 * BEFORE to log method parameters and some additional content
 * AFTER to log method result and some additional content
 * AROUND to log method result, parameters and some additional content
 * AFTER_THROWING to log method throwing an exception, parameters and some additional content
 * AFTER_RETURNING to log method throwing an exception, parameters and some additional content
 */
public enum Entry {
    BEFORE,
    AFTER,
    AROUND,
    AFTER_THROWING,
    AFTER_RETURNING
}
