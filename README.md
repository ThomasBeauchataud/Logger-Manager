# Logger-Manager
Maven repository to easily generate logs message

- [What this library brings more than others logging libraries](#what-this-library-brings-more-than-others-logging-libraries)
- [How to get the Logger service](#how-to-get-the-logger-service)
- [How to use the Logger service](#how-to-use-the-logger-service)  
    - [How to persist a message](#how-to-persist-a-message)
    - [How to format a message](#how-to-format-a-message)
    - [How to log with FormatterHandlers and PersistingHandlers](#how-to-log-with-formatterhandlers-and-persistinghandlers)
        - [Log a simple String object](#log-a-simple-string-object)
        - [Log a LogContent object](#log-a-logcontent-object)
    - [How to trace a method invocation](#how-to-trace-a-method-invocation)
    - [How to create a Map log message with the LogDataCollector](#how-to-create-a-map-log-message-with-the-logdatacollector)
- [Custom your Logger service](#custom-your-logger-service)
    - [Add a new PersistingHandler](#add-a-new-persistinghandler)
    - [Add a new FormatterHandler](#add-a-new-formatterhandler)
- [Logger statistics](#logger-statistics)
- [Versions historic and projects](#versions-historic-and-projects)

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

PersistingHandler getPersistingHandler(Class<?> PersistingHandlerClass);
```
> Example
```
# MyService.java

PersistingHandler SystemOutPersistingHandler = logger.getPersistingHandler(SystemOutPersistingHandler.class);
```
Here are the default *PersistingHandlers* provided with this library :
- *SystemOutPersistingHandler* which persist a message on the system console
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
### Log a LogContent object

## How to trace a method invocation
### Methods trace annotations
Trace annotations are annotation to trace before, after or around method invocation.
You can specify for each method which *FormatterHandler* and/or *PersistingHandler* you want to use or use the default
> The *TraceAnnotationHandler* (see [Plug the aspect class](#plug-the-aspect-class)) use the *LogDataCollector* service (see [How to create a Map log message with the LogDataCollector](#how-to-create-a-map-log-message-with-the-logdatacollector))

Here are the available annotation :
- TraceBefore, this annotation trace a method before his invocation with his parameters
```
# MyService.java

@TraceBefore(persistingHandlerClass = FilePersistingHandler.class)
public Object myMethodToTrace(Object param1, String param2, int param2) {
    ...
}
```
- TraceAround, this annotation trace a method around his invocation with his parameters and his result
```
# MyService.java

@TraceAround(formatterHandlerClass = JsonFormatterHandler.class)
public Object myMethodToTrace(Object param1, String param2, int param2) {
    ...
}
```
- TraceAfter, this annotation trace a method before his invocation with his parameters
```
# MyService.java

@TraceAfter()
public Object myMethodToTrace(Object param1, String param2, int param2) {
    ...
}
```
### Plug the aspect class
To intercept annotated methods invocations, you have to create the aspect class. 
Methods to handle the trace of methods are already implemented in the *TraceAnnotationHandler* class.

This is what should looks like your aspect class to intercept annotated methods invocations
```
@Aspect
public class MyTraceAspectClass extends TraceAnnotationsHandler {

    @Pointcut("@annotation(traceBefore) && execution(* *(..)")
    public void traceBeforePointcut(TraceBefore traceBefore) { }

    @Pointcut("@annotation(traceAround) && execution(* *(..))")
    public void traceAroundPointcut(TraceAround traceAround) { }

    @Pointcut("@annotation(traceAfter) && execution(* *(..)")
    public void traceAfterPointcut(TraceAfter traceAfter) { }

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

}
```
### How to create a Map log message with the LogDataCollector
## Custom your Logger service
You can customise your *Logger* by creating your own *PersistingHandler* and *FormatterHandler*
### Add a new PersistingHandler
To create a new *PersistingHandler*, you just have to create a class implementing the interface *PersistingHandler*
> You can test your *PersistingHandler* by running the test *PersistingHandlerTest.run(PersistingHandler)* on your new *PersistingHandler*

Then to add your new *PersistingHandler* in the *Logger*, you can use the following method
````
# LoggerInterface.java

void addPersistingHandler(PersistingHandler persistingHandler);
````
> Example
````
# MyService.java

logger.addPersistingHandler(new MyPersistingHandler());
````
### Add a new FormatterHandler
To create a new *FormatterHandler*, you just have to create a class implementing the interface *FormatterHandler*
> You can test your *FormatterHandler* by running the test *FormatterHandlerTest.run(FormatterHandler)* on your new *FormatterHandler*

Then to add your new *FormatterHandler* in the *Logger*, you can use the following method
````
# LoggerInterface.java

void addFormatterHandler(FormatterHandler persistingHandler);
````
> Example
````
# MyService.java

logger.addFormatterHandler(new MyFormatterHandler());
````
## Logger statistics
## Versions historic and projects
- 5.0 (Project) Adding a graphic interface to manage the *Logger* service
- 4.0 (Project) The possibility to create, remove and modify logging rules during the runtime
- 3.1 Adding the *LoggerStatisticsManagement* to follow the utilisation of the *Logger* service
- 3.0 Adding traces annotations and the *TraceAnnotationsHandler*
- 2.0 Adding the *LogDataCollector* service and the possibility to log *Map* object
- 1.2 Adding tests to check if the created *PersistingHandler* & *FormatterHandler* are conformed
- 1.1 Adding the possibility to create and add *PersistingHandler* & *FormatterHandler*
- 1.0 Creation of the library