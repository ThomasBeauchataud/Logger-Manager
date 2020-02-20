# Logger-Manager
Maven repository to easily generate logs message

## How to get the logger service
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
Persisting handlers are persisting systems which persist a message.
They implements the *PersistingHandler* interface
```
# PersistingHandler.java

public interface PersistingHandler {
    void persist(String content);
}
```
You can get any persisting handler by using the following method from the logger
```
# LoggerInterface.java

PersistingHandler getPersistingHandler(
    Class<PersistingHandler> PersistingHandlerClass
);
```
```
# MyService.java

PersistingHandler consolePersistingHandler = logger.
    getPersistingHandler(
        ConsolePersistingHandler.getClass()
    );
```
Here are the default persisting handlers provided with this library :
- *ConsolePersistingHandler* which persist a message on the system console
- *FilePersistingHandler* which persist a message on a file.
The file path can be changed by the following method
```
# FilePersistingHandler.java

public void setFilePath(
    String filePath
);
```
```
# MyService.java

FilePersistingHandler fileMQPersistingHandler = logger.
    getPersistingHandler(
        filePersistingHandler.getClass()
    );
fileMQPersistingHandler.setFilePath(
    "new-path.log"
)
```
- *RabbitMQPersistingHandler* which persist a message on a RabbitMQ server.
RabbitMQ client parameters can be modified by the following method
```
# RabbitMQPersistingHandler.java

public void setRabbitParameters(
    String rabbitMQHost, 
    String rabbitMQUser, 
    String rabbitMQPassword, 
    String rabbitMQExchange, 
    String rabbitMQRoutingKey)
```
```
# MyService.java

RabbitMQPersistingHandler rabbitMQPersistingHandler = logger.
    getPersistingHandler(
        rabbitMQPersistingHandler.getClass()
    );
rabbitMQPersistingHandler.setRabbitParameters(
    "localhost",
    "guest",
    "guest",
    "default",
    "*"
)
```
### How to format a message
Persisting handlers are persisting systems which persist a message.
They implements the *PersistingHandler* interface
### How to log with formatter handlers and persisting handlers
#### Log a simple *String*
To log a simple *String* message by using defaults persisting and formatter handlers, you can use the following method provided by the logger
```
# LoggerInterface.java

void log(
    String message, 
    Severity severity);
```
```
# MyService.java

logger.log(
    "my log message", 
    Severity.INFO)
```
If you want to specify which *FormatterHandler* and/or *PersistingHandler* you want to use, you can use the following method
```
# LoggerInterface.java

void log(
    String message, 
    Severity severity, 
    Class<PersistingHandler> persistingHandlerClass, 
    Class<FormatterHandler> formatterHandlerClass
);
```
```
# MyService.java

logger.log(
    "my log message", 
    Severity.INFO,
    FilePersistingHandler.getClass(),
    null
)
```
> Let a *Handler* to *null* to get the default *Handler*
### To log a *Map* object