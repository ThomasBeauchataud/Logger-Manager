package com.github.ffcfalcos.logger.interceptor;

import com.github.ffcfalcos.logger.Logger;
import com.github.ffcfalcos.logger.LoggerInterface;
import com.github.ffcfalcos.logger.collector.LogDataCollector;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;
import java.util.Map;

@SuppressWarnings({"Duplicates","unused"})
public abstract class TraceAnnotationsHandler {

    private LogDataCollector logDataCollector;
    private LoggerInterface logger;

    public TraceAnnotationsHandler() {
        logDataCollector = LogDataCollector.getInstance();
        logger = Logger.getInstance();
    }

    protected void handleTraceBefore(JoinPoint joinPoint, TraceBefore traceBefore) {
        Map<String, Object> logContent = logDataCollector.init("Trace");
        logDataCollector.add(logContent, "parameters", Arrays.toString(joinPoint.getArgs()));
        logger.log(logDataCollector.close(logContent), traceBefore.persistingHandlerClass(), traceBefore.formatterHandlerClass());
    }

    protected Object handleTraceAround(ProceedingJoinPoint proceedingJoinPoint, TraceAround traceAround) throws Throwable {
        Map<String, Object> logContent = logDataCollector.init("Trace");
        try {
            logDataCollector.add(logContent, "parameters", Arrays.toString(proceedingJoinPoint.getArgs()));
            Object result = proceedingJoinPoint.proceed();
            logDataCollector.add(logContent, "response", result.toString());
            return result;
        } catch (Exception e) {
            logDataCollector.addException(logContent, e);
        } finally {
            logger.log(logDataCollector.close(logContent), traceAround.persistingHandlerClass(), traceAround.formatterHandlerClass());
        }
        return null;
    }

    protected void handleTraceAfter() {
        //TODO Implements
    }

}
