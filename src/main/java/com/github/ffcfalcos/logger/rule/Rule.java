package com.github.ffcfalcos.logger.rule;

import org.aspectj.lang.ProceedingJoinPoint;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unused","rawtypes"})
public class Rule implements Serializable {

    private String className;
    private String methodName;
    private Entry entry;
    private Class persistingHandlerClass;
    private Class formatterHandlerClass;

    public Rule(String className, String methodName, Entry entry, Class persistingHandlerClass, Class formatterHandlerClass) {
        this.className = className;
        this.methodName = methodName;
        this.entry = entry;
        this.persistingHandlerClass = persistingHandlerClass;
        this.formatterHandlerClass = formatterHandlerClass;
    }

    public Entry getEntry() {
        return entry;
    }

    public Class getPersistingHandlerClass() {
        return persistingHandlerClass;
    }

    public Class getFormatterHandlerClass() {
        return formatterHandlerClass;
    }

    public boolean validRule(ProceedingJoinPoint proceedingJoinPoint) {
        if(proceedingJoinPoint.getTarget().getClass().getSimpleName().equals(className)) {
            return methodName == null || proceedingJoinPoint.getSignature().getName().equals(methodName);
        }
        return false;
    }

    public List<String> toStringList() {
        return Arrays.asList(className, methodName, entry.name(), persistingHandlerClass.getName(), formatterHandlerClass.getName());
    }

}
