package com.github.ffcfalcos.logger.trace;

import org.aspectj.lang.JoinPoint;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Rule entity
 */
@SuppressWarnings("unused")
public class Rule implements Serializable {

    private String className;
    private String methodName;
    private Entry entry;
    private Class<?> persistingHandlerClass;
    private Class<?> formatterHandlerClass;
    private boolean context;

    /**
     * Rule Constructor
     *
     * @param className              String
     * @param methodName             String
     * @param entry                  Entry
     * @param persistingHandlerClass Class
     * @param formatterHandlerClass  Class
     * @param context                boolean
     */
    public Rule(String className, String methodName, Entry entry, Class<?> persistingHandlerClass, Class<?> formatterHandlerClass, boolean context) {
        this.className = className;
        this.methodName = methodName;
        this.entry = entry;
        this.persistingHandlerClass = persistingHandlerClass;
        this.formatterHandlerClass = formatterHandlerClass;
        this.context = context;
    }

    /**
     * Return the entry
     *
     * @return Entry
     */
    public Entry getEntry() {
        return entry;
    }

    /**
     * Return the PersistingHandlerClass
     *
     * @return Class
     */
    public Class<?> getPersistingHandlerClass() {
        return persistingHandlerClass;
    }

    /**
     * Return the FormatterHandlerClass
     *
     * @return Class
     */
    public Class<?> getFormatterHandlerClass() {
        return formatterHandlerClass;
    }

    /**
     * Return true if the rule has to log the context
     *
     * @return boolean
     */
    public boolean getContext() {
        return context;
    }

    /**
     * Validate the Rule for a JointPoint
     *
     * @param joinPoint JointPoint
     * @return boolean
     */
    public boolean validRule(JoinPoint joinPoint) {
        if (joinPoint.getTarget().getClass().getName().equals(className)) {
            return methodName == null || joinPoint.getSignature().getName().equals(methodName);
        }
        return false;
    }

    /**
     * Return a Rule on a string list format
     *
     * @return String[]
     */
    public List<String> toStringList() {
        String persistingHandlerClassName;
        if (persistingHandlerClass == null) {
            persistingHandlerClassName = null;
        } else {
            persistingHandlerClassName = persistingHandlerClass.getName();
        }
        String formatterHandlerClassName;
        if (formatterHandlerClass == null) {
            formatterHandlerClassName = null;
        } else {
            formatterHandlerClassName = formatterHandlerClass.getName();
        }
        return Arrays.asList(className, methodName, entry.name(), persistingHandlerClassName, formatterHandlerClassName, Boolean.toString(context));
    }

    /**
     * Return true if Rule are equals
     *
     * @param rule Rule
     * @return boolean
     */
    public boolean equalsTo(Rule rule) {
        return rule.entry == this.entry
                && rule.formatterHandlerClass == this.formatterHandlerClass
                && rule.persistingHandlerClass == this.persistingHandlerClass
                && rule.className.equals(this.className)
                && rule.methodName.equals(this.methodName);

    }

}
