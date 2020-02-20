# Logger-Manager
Maven repository to easily generate logs message

- [How to get the Logger service](#how-to-get-the-Logger-service)
- How to use the *Logger* service
    - How to persist a message
    - How to format a message
    - How to log with *FormatterHandlers* and *PersistingHandlers*
        - Log a simple *String*
        - Log a *Map* object
    - How to trace a method invocation
    - How to create a *Map* log message with the *LogDataCollector*
- Custom your *Logger* service
    - Add new *PersistingHandlers*
    - Add new *FormatterHandlers*


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
> Dont use both system cause they would not be the same instance
## How to use the logger service
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
```
# MyService.java

RabbitMQPersistingHandler rabbitMQPersistingHandler = logger.getPersistingHandler(rabbitMQPersistingHandler.class);
rabbitMQPersistingHandler.setRabbitParameters("localhost","guest", "guest", "default", "*")
```
### How to format a message
*FormatterHandlers* are persisting systems which format a message.
They implements the *FormatterHandler* interface
### How to log with *FormatterHandlers* and *PersistingHandlers*
#### Log a simple *String*
To log a simple *String* message by using defaults *FormatterHandler* and *PersistingHandler*, you can use the following method provided by the *Logger*
```
# LoggerInterface.java

void log(String message, Severity severity);
```
```
# MyService.java

logger.log("my log message", Severity.INFO)
```
If you want to specify which *FormatterHandler* and/or *PersistingHandler* you want to use, you can use the following method
```
# LoggerInterface.java

void log(String message, Severity severity, Class persistingHandlerClass, Class formatterHandlerClass);
```
```
# MyService.java

logger.log("my log message", Severity.INFO, FilePersistingHandler.class, null)
```
> Let a *Handler* to *null* to get the default *Handler* of the *Logger*
### To log a *Map* object