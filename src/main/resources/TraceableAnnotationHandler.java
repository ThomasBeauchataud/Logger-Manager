import com.github.ffcfalcos.logger.trace.AbstractTraceableAnnotationHandler;
import com.github.ffcfalcos.logger.trace.FileWatcherRulesLoader;
import com.github.ffcfalcos.logger.trace.CsvRulesStorageHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class TraceableAnnotationHandler extends AbstractTraceableAnnotationHandler {

    protected TraceableAnnotationHandler() {
        super(new FileWatcherRulesLoader(new CsvRulesStorageHandler()));
    }

    @Pointcut("@annotation(com.github.ffcfalcos.logger.trace.Traceable) && execution(* *(..))")
    public void traceablePointcut() { }

    @Around("traceablePointcut()")
    public Object traceable(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.handle(proceedingJoinPoint);
    }

}