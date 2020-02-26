package com.github.ffcfalcos.logger.rule;

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

    /**
     * Rule Constructor
     * @param className String
     * @param methodName String
     * @param entry Entry
     * @param persistingHandlerClass Class
     * @param formatterHandlerClass Class
     */
    public Rule(String className, String methodName, Entry entry, Class<?> persistingHandlerClass, Class<?> formatterHandlerClass) {
        this.className = className;
        this.methodName = methodName;
        this.entry = entry;
        this.persistingHandlerClass = persistingHandlerClass;
        this.formatterHandlerClass = formatterHandlerClass;
    }

    /**
     * Return the entry
     * @return Entry
     */
    public Entry getEntry() {
        return entry;
    }

    /**
     * Return the PersistingHandlerClass
     * @return Class
     */
    public Class<?> getPersistingHandlerClass() {
        return persistingHandlerClass;
    }

    /**
     * Return the FormatterHandlerClass
     * @return Class
     */
    public Class<?> getFormatterHandlerClass() {
        return formatterHandlerClass;
    }

    /**
     * Validate the Rule for a JointPoint
     * @param joinPoint JointPoint
     * @return boolean
     */
    public boolean validRule(JoinPoint joinPoint) {
        if(joinPoint.getTarget().getClass().getSimpleName().equals(className)) {
            return methodName == null || joinPoint.getSignature().getName().equals(methodName);
        }
        return false;
    }

    /**
     * Return a Rule on a string list format
     * @return String[]
     */
    public List<String> toStringList() {
        return Arrays.asList(className, methodName, entry.name(), persistingHandlerClass.getName(), formatterHandlerClass.getName());
    }

}
