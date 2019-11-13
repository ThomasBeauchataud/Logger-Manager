package com.github.ffcfalcos.logger.interceptor;

import com.github.ffcfalcos.logger.LoggerInterface;
import com.github.ffcfalcos.logger.collector.LogDataCollector;
import com.github.ffcfalcos.logger.collector.LogDataCollectorInterface;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Aspect
@SuppressWarnings("Duplicates")
public class TraceInterceptor {

    private LogDataCollectorInterface logDataCollector = new LogDataCollector();

    @Pointcut("@annotation(traceBefore)")
    public void traceBeforePointcut(TraceBefore traceBefore) { }

    @Pointcut("@annotation(traceAround)")
    public void traceAroundPointcut(TraceAround traceAround) { }

    @Before(value = "traceBeforePointcut(traceBefore)", argNames = "joinPoint, traceBefore")
    public void logBefore(JoinPoint joinPoint, TraceBefore traceBefore) {
        Map<String, Object> logContent = logDataCollector.init("Trace");
        logDataCollector.add(logContent, "parameters", Arrays.toString(joinPoint.getArgs()));
        TraceableInterface traceable = (TraceableInterface) joinPoint.getTarget();
        LoggerInterface logger = traceable.getLogger();
        logger.log(logDataCollector.close(logContent), traceBefore.persistingHandlerName(), traceBefore.formatterHandlerName());
    }

    @Around(value = "traceAroundPointcut(traceAround)", argNames = "proceedingJoinPoint, traceAround")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint, TraceAround traceAround) throws Throwable {
        Map<String, Object> logContent = logDataCollector.init("Trace");
        try {
            logDataCollector.add(logContent, "parameters", Arrays.toString(proceedingJoinPoint.getArgs()));
            Object result = proceedingJoinPoint.proceed();
            logDataCollector.add(logContent, "response", result.toString());
            return result;
        } catch (Exception e) {
            logDataCollector.addException(logContent, e);
        } finally {
            TraceableInterface traceable = (TraceableInterface) proceedingJoinPoint.getTarget();
            LoggerInterface logger = traceable.getLogger();
            logger.log(logDataCollector.close(logContent), traceAround.persistingHandlerName(), traceAround.formatterHandlerName());
        }
        return null;
    }

}
