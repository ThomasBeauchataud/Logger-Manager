# Logger-Manager
Maven repository to easily generate logs message

- [How to get the Logger service](#how-to-get-the-logger-service)
- [How to use the Logger service](#how-to-use-the-logger-service)  
    - [How to persist a message](#how-to-persist-a-message)
    - [How to format a message](#how-to-format-a-message)
    - [How to log with FormatterHandlers and PersistingHandlers](#how-to-log-with-formatterhandlers-and-persistinghandlers)
        - [Log a simple String object](#log-a-simple-string-object)
        - [Log a Map object](#log-a-map-object)
    - [How to trace a method invocation](#how-to-trace-a-method-invocation)
    - [How to create a Map log message with the LogDataCollector](#how-to-create-a-map-log-message-with-the-logdatacollector)
- [Custom your Logger service](#custom-your-logger-service)
    - [Add a new PersistingHandler](#add-a-new-persistingHandler)
    - [Add a new FormatterHandler](#add-a-new-formatterHandler)

## How to get the Logger service
- By using a static method to get the singleton
```
# MyService.java

LoggerInterface logger = Logger.getInstance();
```
- By using dependency injection
```
# MyService.java

@Inject
LoggerInterface logger;
```
> If you use dependency injection, the static method of the singleton will return the same instance
## How to use the Logger service
### How to persist a message
*PersistingHandlers* are persisting systems which persist a message.
They implements the *PersistingHandler* interface
```
# PersistingHandler.java

public interface PersistingHandler {
    void persist(String content);
}
```
You can get any *PersistingHandler* by using the following method from the *Logger*
```
# LoggerInterface.java

PersistingHandler getPersistingHandler(Class PersistingHandlerClass);
```
> Example
```
# MyService.java

PersistingHandler consolePersistingHandler = logger.getPersistingHandler(ConsolePersistingHandler.class);
```
Here are the default *PersistingHandlers* provided with this library :
- *ConsolePersistingHandler* which persist a message on the system console
- *FilePersistingHandler* which persist a message on a file.
The file path can be changed by the following method
```
# FilePersistingHandler.java

public void setFilePath(String filePath);
```
> Example
```
# MyService.java

FilePersistingHandler fileMQPersistingHandler = logger.getPersistingHandler(filePersistingHandler.class);
fileMQPersistingHandler.setFilePath("new-path.log")
```
- *RabbitMQPersistingHandler* which persist a message on a RabbitMQ server.
RabbitMQ client parameters can be modified by the following method
```
# RabbitMQPersistingHandler.java

public void setRabbitParameters(String rabbitMQHost, String rabbitMQUser, String rabbitMQPassword, String rabbitMQExchange, String rabbitMQRoutingKey)
```
> Example
```
# MyService.java

RabbitMQPersistingHandler rabbitMQPersistingHandler = logger.getPersistingHandler(rabbitMQPersistingHandler.class);
rabbitMQPersistingHandler.setRabbitParameters("localhost","guest", "guest", "default", "*")
```
### How to format a message
*FormatterHandlers* are persisting systems which format a message.
They implements the *FormatterHandler* interface
### How to log with *FormatterHandlers* and *PersistingHandlers*
#### Log a simple String object
To log a simple *String* message by using defaults *FormatterHandler* and *PersistingHandler*, you can use the following method provided by the *Logger*
```
# LoggerInterface.java

void log(String message, Severity severity);
```
> Example
```
# MyService.java

logger.log("my log message", Severity.INFO)
```
If you want to specify which *FormatterHandler* and/or *PersistingHandler* you want to use, you can use the following method
```
# LoggerInterface.java

void log(String message, Severity severity, Class persistingHandlerClass, Class formatterHandlerClass);
```
> Example
```
# MyService.java

logger.log("my log message", Severity.INFO, FilePersistingHandler.class, null)
```
> Let a *Handler* to *null* to get the default *Handler* of the *Logger*
### Log a Map object

## How to trace a method invocation
### Methods annotations
### Plug the aspect class
To intercept annotated method invocation, you have to create the aspect class. 
Methods to handle the trace of methods are already implemented in the *TraceAnnotationHandler* class.

This is what should looks like your aspect class to intercept annotated method invocation
```
@Aspect
public class MyTraceAspectClass extends TraceAnnotationsHandler {

    @Pointcut("@annotation(traceBefore)")
    public void traceBeforePointcut(TraceBefore traceBefore) { }
    
    @Pointcut("@annotation(traceAround)")
    public void traceAroundPointcut(TraceAround traceAround) { }
    
    @Pointcut("@annotation(traceAfter)")
    public void traceAroundPointcut(TraceAround traceAfter) { }
    
    @Before(value = "traceBeforePointcut(traceBefore)", argNames = "joinPoint, traceBefore")
    public void traceBefore(JoinPoint joinPoint, TraceBefore traceBefore) {
        return super.handleTraceBefore(jointPoint, traceBefore)
    }
    
    @Around(value = "traceAroundPointcut(traceAround)", argNames = "proceedingJoinPoint, traceAround")
    public Object traceAround(ProceedingJoinPoint proceedingJoinPoint, TraceAround traceAround) {
        return super.handleTraceAround(proceedingJoinPoint, traceAround)
    }
    
    @After(value = "traceAfterPointcut(traceAfter)", argNames = "result, traceAfter")
    public void traceAfter(Object result, TraceAfter traceAfter) {
        return super.handleTraceAfter(result, traceAfter)
    }

}
```
### How to create a Map log message with the LogDataCollector
## Custom your Logger service
### Add a new PersistingHandler
### Add a new FormatterHandler