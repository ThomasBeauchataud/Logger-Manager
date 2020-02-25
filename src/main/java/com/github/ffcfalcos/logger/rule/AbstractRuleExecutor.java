package com.github.ffcfalcos.logger.rule;

import com.github.ffcfalcos.logger.Logger;
import com.github.ffcfalcos.logger.LoggerInterface;
import com.github.ffcfalcos.logger.collector.LogContent;
import com.github.ffcfalcos.logger.rule.storage.RuleStorageHandler;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public abstract class AbstractRuleExecutor {

    private List<Rule> rules;
    private LoggerInterface logger;

    protected AbstractRuleExecutor(RuleStorageHandler ruleStorageHandler) {
        this.rules = new ArrayList<>();
        this.logger = Logger.getInstance();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new RuleLoader(rules, ruleStorageHandler), 0, 60, TimeUnit.MINUTES);
    }

    protected Object handle(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Rule rule = getRule(proceedingJoinPoint);
        if(rule != null) {
            if(rule.getEntry().equals(Entry.before)) {
                LogContent logContent = new LogContent("trace");
                logContent.put("parameters", proceedingJoinPoint.getArgs());
                logContent.put("method", proceedingJoinPoint.getSignature().getName());
                logContent.put("class", proceedingJoinPoint.getTarget().getClass().getName());
                logger.log(logContent.close(), rule.getPersistingHandlerClass(), rule.getFormatterHandlerClass());
                return proceedingJoinPoint.proceed();
            }
            if(rule.getEntry().equals(Entry.after)) {
                Object response = proceedingJoinPoint.proceed();
                LogContent logContent = new LogContent("trace");
                logContent.put("response", response);
                logContent.put("method", proceedingJoinPoint.getSignature().getName());
                logContent.put("class", proceedingJoinPoint.getTarget().getClass().getName());
                logger.log(logContent.close(), rule.getPersistingHandlerClass(), rule.getFormatterHandlerClass());
                return response;
            }
            if(rule.getEntry().equals(Entry.around)) {
                LogContent logContent = new LogContent("trace");
                logContent.put("parameters", proceedingJoinPoint.getArgs());
                logContent.put("method", proceedingJoinPoint.getSignature().getName());
                logContent.put("class", proceedingJoinPoint.getTarget().getClass().getName());
                Object response = proceedingJoinPoint.proceed();
                logContent.put("response", response);
                logger.log(logContent.close(), rule.getPersistingHandlerClass(), rule.getFormatterHandlerClass());
                return response;
            }
        }
        return proceedingJoinPoint.proceed();
    }

    private Rule getRule(ProceedingJoinPoint proceedingJoinPoint) {
        for(Rule rule : rules) {
            if(rule.validRule(proceedingJoinPoint)) {
                return rule;
            }
        }
        return null;
    }

}
