package com.github.ffcfalcos.logger.rule;

import com.github.ffcfalcos.logger.Logger;
import com.github.ffcfalcos.logger.LoggerInterface;
import com.github.ffcfalcos.logger.collector.LogDataCollector;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public abstract class RuleExecutor {

    private List<Rule> rules;
    private LoggerInterface logger;
    private LogDataCollector logDataCollector;

    public RuleExecutor() {
        this.rules = new ArrayList<>();
        this.logger = Logger.getLogger();
        this.logDataCollector = LogDataCollector.getLogDataCollector();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new RuleLoader(rules, logger), 0, 60, TimeUnit.MINUTES);
    }

    protected Object handle(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Rule rule = getRule(proceedingJoinPoint);
        if(rule != null) {
            if(rule.getEntry().equals(Entry.before)) {
                Map<String, Object> logContent = logDataCollector.init("trace");
                logDataCollector.add(logContent, "parameters", proceedingJoinPoint.getArgs());
                logDataCollector.add(logContent, "method", proceedingJoinPoint.getSignature().getName());
                logDataCollector.add(logContent, "class", proceedingJoinPoint.getTarget().getClass().getName());
                logger.log(logDataCollector.close(logContent), rule.getPersistingHandlerClass(), rule.getFormatterHandlerClass());
                return proceedingJoinPoint.proceed();
            }
            if(rule.getEntry().equals(Entry.after)) {
                Object response = proceedingJoinPoint.proceed();
                Map<String, Object> logContent = logDataCollector.init("trace");
                logDataCollector.add(logContent, "response", response);
                logDataCollector.add(logContent, "method", proceedingJoinPoint.getSignature().getName());
                logDataCollector.add(logContent, "class", proceedingJoinPoint.getTarget().getClass().getName());
                logger.log(logDataCollector.close(logContent), rule.getPersistingHandlerClass(), rule.getFormatterHandlerClass());
                return response;
            }
            if(rule.getEntry().equals(Entry.around)) {
                Map<String, Object> logContent = logDataCollector.init("trace");
                logDataCollector.add(logContent, "parameters", proceedingJoinPoint.getArgs());
                logDataCollector.add(logContent, "method", proceedingJoinPoint.getSignature().getName());
                logDataCollector.add(logContent, "class", proceedingJoinPoint.getTarget().getClass().getName());
                Object response = proceedingJoinPoint.proceed();
                logDataCollector.add(logContent, "response", response);
                logger.log(logDataCollector.close(logContent), rule.getPersistingHandlerClass(), rule.getFormatterHandlerClass());
                return response;
            }
        }
        return proceedingJoinPoint.proceed();
    }

    public abstract Object logExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable;

    private Rule getRule(ProceedingJoinPoint proceedingJoinPoint) {
        for(Rule rule : rules) {
            if(rule.validRule(proceedingJoinPoint)) {
                return rule;
            }
        }
        return null;
    }

}
