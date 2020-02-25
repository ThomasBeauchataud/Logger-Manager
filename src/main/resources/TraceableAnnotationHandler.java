import com.github.ffcfalcos.logger.rule.AbstractTraceableAnnotationHandler;
import com.github.ffcfalcos.logger.rule.storage.CsvRuleStorageHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class TraceableAnnotationHandler extends AbstractTraceableAnnotationHandler {

    protected TraceableAnnotationHandler() {
        super(new CsvRuleStorageHandler());
    }

    @Pointcut("@annotation(com.github.ffcfalcos.logger.rule.Traceable) && execution(* *(..))")
    public void traceablePointcut() { }

    @Around("traceablePointcut()")
    public Object traceable(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.handle(proceedingJoinPoint);
    }

}