package com.github.ffcfalcos.logger.interceptor;

import com.github.ffcfalcos.logger.Logger;
import com.github.ffcfalcos.logger.LoggerInterface;
import com.github.ffcfalcos.logger.collector.LogContent;
import com.github.ffcfalcos.logger.collector.LogType;
import com.github.ffcfalcos.logger.util.SerializerService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Handle Trace Annotation
 * This abstract class is use to be an aspect class, any class extending it must be an aspect class having pointcut
 *      executing methods above
 */
@SuppressWarnings({"Duplicates","unused"})
public abstract class AbstractTraceAnnotationHandler {
    
    private LoggerInterface logger;

    /**
     * AbstractTraceAnnotationHandler Constructor
     * Initialize the Logger instance
     */
    public AbstractTraceAnnotationHandler() {
        logger = Logger.getInstance();
    }

    /**
     * Handle the trace of a method using TraceBefore annotation
     * @param joinPoint JoinPoint
     * @param traceBefore TraceBefore
     */
    protected void handleTraceBefore(JoinPoint joinPoint, TraceBefore traceBefore) {
        LogContent logContent = new LogContent(LogType.TRACE_BEFORE);
        logContent.put("parameters", Arrays.toString(joinPoint.getArgs()));
        if(joinPoint.getTarget() == null) {
            logContent.put("class", joinPoint.getStaticPart().getSourceLocation().getWithinType().getName());
            logContent.put("function", joinPoint.getSignature().getName());
        } else {
            logContent.put("class", joinPoint.getTarget().getClass().getName());
            logContent.put("method", joinPoint.getSignature().getName());
        }
        if(traceBefore.context()) {
            addContext(logContent, joinPoint.getTarget());
        }
        logger.log(logContent.close(), traceBefore.persistingHandlerClass(), traceBefore.formatterHandlerClass());
    }

    /**
     * Handle the trace of a method using TraceAround annotation
     * @param proceedingJoinPoint ProceedingJoinPoint
     * @param traceAround TraceAround
     * @return Object, the result of the invoked method
     * @throws Throwable if there is an exception during the method invocation
     */
    protected Object handleTraceAround(ProceedingJoinPoint proceedingJoinPoint, TraceAround traceAround) throws Throwable {
        LogContent logContent = new LogContent(LogType.TRACE_AROUND);
        if(proceedingJoinPoint.getTarget() == null) {
            logContent.put("class", proceedingJoinPoint.getStaticPart().getSourceLocation().getWithinType().getName());
            logContent.put("function", proceedingJoinPoint.getSignature().getName());
        } else {
            logContent.put("class", proceedingJoinPoint.getTarget().getClass().getName());
            logContent.put("method", proceedingJoinPoint.getSignature().getName());
        }
        logContent.put("parameters", Arrays.toString(proceedingJoinPoint.getArgs()));
        if(traceAround.context()) {
            addContext(logContent, proceedingJoinPoint.getTarget());
        }
        try {
            Object result = proceedingJoinPoint.proceed();
            logContent.put("response", result.toString());
            logger.log(logContent.close(), traceAround.persistingHandlerClass(), traceAround.formatterHandlerClass());
            return result;
        } catch (Exception e) {
            logContent.addException(e);
            logger.log(logContent.close(), traceAround.persistingHandlerClass(), traceAround.formatterHandlerClass());
            throw e;
        }
    }

    /**
     * Handle the trace of a method using TraceAfter annotation
     * @param joinPoint JoinPoint
     * @param traceAfter TraceBefore
     */
    protected void handleTraceAfter(JoinPoint joinPoint, TraceAfter traceAfter) {
        LogContent logContent = new LogContent(LogType.TRACE_AFTER);
        logContent.put("parameters", Arrays.toString(joinPoint.getArgs()));
        if(joinPoint.getTarget() == null) {
            logContent.put("class", joinPoint.getStaticPart().getSourceLocation().getWithinType().getName());
            logContent.put("function", joinPoint.getSignature().getName());
        } else {
            logContent.put("class", joinPoint.getTarget().getClass().getName());
            logContent.put("method", joinPoint.getSignature().getName());
        }
        if(traceAfter.context()) {
            addContext(logContent, joinPoint.getTarget());
        }
        logger.log(logContent.close(), traceAfter.persistingHandlerClass(), traceAfter.formatterHandlerClass());
    }

    /**
     * Handle the trace of a method using TraceAfterThrowing annotation
     * @param joinPoint JoinPoint
     * @param exception Exception, the exception thrown by the method
     * @param traceAfterThrowing TraceAfterThrowing
     */
    protected void handleTraceAfterThrowing(JoinPoint joinPoint, Exception exception, TraceAfterThrowing traceAfterThrowing) {
        LogContent logContent = new LogContent(LogType.TRACE_AFTER_THROWING);
        logContent.put("parameters", Arrays.toString(joinPoint.getArgs()));
        if(joinPoint.getTarget() == null) {
            logContent.put("class", joinPoint.getStaticPart().getSourceLocation().getWithinType().getName());
            logContent.put("function", joinPoint.getSignature().getName());
        } else {
            logContent.put("class", joinPoint.getTarget().getClass().getName());
            logContent.put("method", joinPoint.getSignature().getName());
        }
        logContent.addException(exception);
        if(traceAfterThrowing.context()) {
            addContext(logContent, joinPoint.getTarget());
        }
        logger.log(logContent.close(), traceAfterThrowing.persistingHandlerClass(), traceAfterThrowing.formatterHandlerClass());
    }

    /**
     * Handle the trace of a method using TraceAfterReturning annotation
     * @param joinPoint JoinPoint
     * @param output Object, the output of the method
     * @param traceAfterReturning TraceAfterReturning
     */
    protected void handleTraceAfterReturning(JoinPoint joinPoint, Object output, TraceAfterReturning traceAfterReturning) {
        LogContent logContent = new LogContent(LogType.TRACE_AFTER_RETURNING);
        logContent.put("parameters", Arrays.toString(joinPoint.getArgs()));
        if(joinPoint.getTarget() == null) {
            logContent.put("class", joinPoint.getStaticPart().getSourceLocation().getWithinType().getName());
            logContent.put("function", joinPoint.getSignature().getName());
        } else {
            logContent.put("class", joinPoint.getTarget().getClass().getName());
            logContent.put("method", joinPoint.getSignature().getName());
        }
        logContent.put("response", output);
        if(traceAfterReturning.context()) {
            addContext(logContent, joinPoint.getTarget());
        }
        logger.log(logContent.close(), traceAfterReturning.persistingHandlerClass(), traceAfterReturning.formatterHandlerClass());
    }

    /**
     * Serialize the target into a map object
     * @param logContent LogContent
     * @param target Object
     */
    private void addContext(LogContent logContent, Object target) {
        logContent.put("target-context", SerializerService.serializeJson(target));
    }

}
