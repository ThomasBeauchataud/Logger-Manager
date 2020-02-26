package com.github.ffcfalcos.logger.rule;

import com.github.ffcfalcos.logger.Logger;
import com.github.ffcfalcos.logger.LoggerInterface;
import com.github.ffcfalcos.logger.collector.LogContent;
import com.github.ffcfalcos.logger.rule.loader.AbstractRulesLoader;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Handle Traceable annotation
 * Intercept methods with the Traceable and compare them to the rule list
 * If the methods is in the rule list, it will be logged as the rule wants it
 */
@SuppressWarnings("unused")
public abstract class AbstractTraceableAnnotationHandler {

    private List<Rule> rules;
    private LoggerInterface logger;

    /**
     * AbstractTraceableAnnotationHandler Constructor
     * @param rulesLoader AbstractRulesLoader
     */
    protected AbstractTraceableAnnotationHandler(AbstractRulesLoader rulesLoader) {
        this.rules = rulesLoader.getRulesStorageHandler().getRules();
        this.logger = Logger.getInstance();
        rulesLoader.setRules(rules);
        new Thread(rulesLoader).start();
    }

    /**
     * Handle the invocation of the ProceedingJoinPoint of log with the associated Rule if it exists
     * @param proceedingJoinPoint ProceedingJoinPoint
     * @return Object
     * @throws Throwable if there is an exception during the method invocation
     */
    protected Object handle(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Rule rule = getRule(proceedingJoinPoint);
        if(rule != null) {
            if(rule.getEntry().equals(Entry.BEFORE)) {
                LogContent logContent = new LogContent("trace");
                logContent.put("parameters", proceedingJoinPoint.getArgs());
                logContent.put("method", proceedingJoinPoint.getSignature().getName());
                logContent.put("class", proceedingJoinPoint.getTarget().getClass().getName());
                logger.log(logContent.close(), rule.getPersistingHandlerClass(), rule.getFormatterHandlerClass());
                return proceedingJoinPoint.proceed();
            }
            if(rule.getEntry().equals(Entry.AFTER)) {
                Object response = proceedingJoinPoint.proceed();
                LogContent logContent = new LogContent("trace");
                logContent.put("response", response);
                logContent.put("method", proceedingJoinPoint.getSignature().getName());
                logContent.put("class", proceedingJoinPoint.getTarget().getClass().getName());
                logger.log(logContent.close(), rule.getPersistingHandlerClass(), rule.getFormatterHandlerClass());
                return response;
            }
            if(rule.getEntry().equals(Entry.AROUND)) {
                LogContent logContent = new LogContent("trace");
                logContent.put("parameters", proceedingJoinPoint.getArgs());
                logContent.put("method", proceedingJoinPoint.getSignature().getName());
                logContent.put("class", proceedingJoinPoint.getTarget().getClass().getName());
                Object response = proceedingJoinPoint.proceed();
                logContent.put("response", response);
                logger.log(logContent.close(), rule.getPersistingHandlerClass(), rule.getFormatterHandlerClass());
                return response;
            }
            if(rule.getEntry().equals(Entry.AFTER_THROWING)) {
                LogContent logContent = new LogContent("trace");
                try {
                    return proceedingJoinPoint.proceed();
                } catch (Exception e) {
                    logContent.put("parameters", proceedingJoinPoint.getArgs());
                    logContent.put("method", proceedingJoinPoint.getSignature().getName());
                    logContent.put("class", proceedingJoinPoint.getTarget().getClass().getName());
                    logContent.addException(e);
                    logger.log(logContent.close(), rule.getPersistingHandlerClass(), rule.getFormatterHandlerClass());
                    throw e;
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }

    /**
     * Return a the rule associated to the JointPoint or null if there is no rule corresponding
     * @param proceedingJoinPoint ProceedingJoinPoint
     * @return Rule
     */
    private Rule getRule(ProceedingJoinPoint proceedingJoinPoint) {
        for(Rule rule : rules) {
            if(rule.validRule(proceedingJoinPoint)) {
                return rule;
            }
        }
        return null;
    }

}
