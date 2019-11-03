package com.github.ffcfalcos.logger.interceptor;

import com.github.ffcfalcos.logger.LoggerInterface;
import com.github.ffcfalcos.logger.formatter.LogInterceptorFormatterInterface;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import java.util.List;

@Aspect
public class LoggerInterceptor {

    @Inject
    private LoggerInterface logger;
    @Inject
    private LogInterceptorFormatterInterface logInterceptorFormatter;

    @Before("@within(com.github.ffcfalcos.logger.LogBefore)")
    public void logBefore(JoinPoint joinPoint) {
        List<JSONObject> logContent = logInterceptorFormatter.init();
        logInterceptorFormatter.addParameters(logContent, joinPoint.getArgs());
        logger.log(logInterceptorFormatter.close(logContent));
    }

    @After("@within(com.github.ffcfalcos.logger.LogAfter)")
    public void logAfter(JoinPoint joinPoint) {
        List<JSONObject> logContent = logInterceptorFormatter.init();
        logInterceptorFormatter.addParameters(logContent, joinPoint.getArgs());
        logger.log(logInterceptorFormatter.close(logContent));
    }

    @Around("@within(com.github.ffcfalcos.logger.LogBefore)")
    public Object logBefore(ProceedingJoinPoint proceedingJoinPoint) {
        List<JSONObject> logContent = logInterceptorFormatter.init();
        try {
            logInterceptorFormatter.addParameters(logContent, proceedingJoinPoint.getArgs());
            Object result = proceedingJoinPoint.proceed();
            logInterceptorFormatter.addResponse(logContent, result);
            return result;
        } catch (Exception e) {
            logInterceptorFormatter.addException(logContent, e);
            return null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        } finally {
            logger.log(logInterceptorFormatter.close(logContent));
        }
    }

}
