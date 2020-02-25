import com.github.ffcfalcos.logger.interceptor.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class TraceAnnotationHandler extends AbstractTraceAnnotationHandler {

    @Pointcut("@annotation(traceBefore) && execution(* *(..))")
    public void traceBeforePointcut(TraceBefore traceBefore) { }

    @Pointcut("@annotation(traceAround) && execution(* *(..))")
    public void traceAroundPointcut(TraceAround traceAround) { }

    @Pointcut("@annotation(traceAfter) && execution(* *(..))")
    public void traceAfterPointcut(TraceAfter traceAfter) { }

    @Pointcut("@annotation(traceAfterThrowing) && execution(* *(..))")
    public void traceAfterThrowingPointcut(TraceAfterThrowing traceAfterThrowing) { }

    @Pointcut("@annotation(traceAfterReturning) && execution(* *(..))")
    public void traceAfterReturningPointcut(TraceAfterReturning traceAfterReturning) { }

    @Before(value = "traceBeforePointcut(traceBefore)", argNames = "joinPoint, traceBefore")
    public void traceBefore(JoinPoint joinPoint, TraceBefore traceBefore) {
        super.handleTraceBefore(joinPoint, traceBefore);
    }

    @Around(value = "traceAroundPointcut(traceAround)", argNames = "proceedingJoinPoint, traceAround")
    public Object traceAround(ProceedingJoinPoint proceedingJoinPoint, TraceAround traceAround) throws Throwable {
        return super.handleTraceAround(proceedingJoinPoint, traceAround);
    }

    @After(value = "traceAfterPointcut(traceAfter)", argNames = "joinPoint, traceAfter")
    public void traceAfter(JoinPoint joinPoint, TraceAfter traceAfter) {
        super.handleTraceAfter(joinPoint, traceAfter);
    }

    @AfterThrowing(value = "traceAfterThrowingPointcut(traceAfterThrowing)", throwing="exception", argNames = "joinPoint, exception, traceAfterThrowing")
    public void traceAfterThrowing(JoinPoint joinPoint, Exception exception, TraceAfterThrowing traceAfterThrowing) {
        super.handleTraceAfterThrowing(joinPoint, exception, traceAfterThrowing);
    }

    @AfterReturning(value = "traceAfterReturningPointcut(traceAfterReturning)", returning="output", argNames = "joinPoint, output, traceAfterReturning")
    public void traceAfterReturning(JoinPoint joinPoint, Object output, TraceAfterReturning traceAfterReturning) {
        super.handleTraceAfterReturning(joinPoint, output, traceAfterReturning);
    }

}