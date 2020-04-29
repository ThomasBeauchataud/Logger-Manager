# Logger-Manager
Maven repository to easily generate logs message
## 1. Install the Logger component
Add the dependency in your pom.xml file
```
<dependency>
    <groupId>com.github.ffcfalcos</groupId>
    <artifactId>logger-manager</artifactId>
    <version>4.0.7-RELEASE</version>
</dependency>
```
If you want to full components options, add the maven plugin below in your pom.xml to install aspect class
```
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>1.4</version>
    <configuration>
        <source>1.8</source>
        <aspectLibraries>
            <aspectLibrary>
                <groupId>com.github.ffcfalcos</groupId>
                <artifactId>logger-manager</artifactId>
            </aspectLibrary>
        </aspectLibraries>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>compile</goal>
                <goal>test-compile</goal>
            </goals>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjtools</artifactId>
            <version>1.9.5</version>
        </dependency>
    </dependencies>
</plugin>
```
## 2. Log with the Logger component
Get the service with singleton option or by dependency injection and log a message
```
LoggerInterface logger = Logger.getInstance();

logger.log("Here is my first message");
```
```
@Inject
LoggerInterface logger;

logger.log("Here is my first message", Severity.INFO);
```
You can specify which *FormatterHandler* and/or *PersistingHandler* you want to use 
```
logger.log("Here is my first message", Severity.INFO, FilePersistingHandler.class, JsonFormatterHandler.class);
```
> Set the *FormatterHandler* or *PersistingHandler* to null to use default

You can use to *LogContent* object to log consistent log message
```
try {
    //do your stuff
    throw new Exception("Exception message");
} catch (Exception e) {
    LogContent logContent = new LogContent(LogType.TRACE_AFTER_THROWING);
    logContent.put("content", "put a custom message here");
    logContent.addException(e);
    Logger.getInstance().log(logContent.close(Severity.CRITICAL), SystemOutPersistingHandler.class, StringFormatterHandler.class);
}
```
```
[Wed Apr 08 14:31:33 EDT 2020] {severity=CRITICAL, start=1586370693362, cause=null, end=1586370693362, time=0, type=TRACE_AFTER_THROWING, error=true, message=Exception message, content=put a custom message here}
```
### 3. Customize your Logger component
You can manage existing *FormatterHandler* & *PersistingHandler* and/or create your own by getting the associated provider
```
Provider<PersistingHandler> persistingHandlerProvider = logger.getPersistingHandlerProvider();

((FilePersistingHandler)persistingHandlerProvider.get(FilePersistingHandler.class)).setFilePath("new-path.loh");

persistingHandlerProvider.add(new MyNewPersistingHandler());
persistingHandlerProvider.setDefault(MyNewPersistingHandler.class)
```
```
Provider<FormatterHandler> formatterHandlerProvider = logger.getFormatterHandlerProvider();

formatterHandlerProvider.add(new MyNewFormatterHandler());
formatterHandlerProvider.setDefault(MyNewFormatterHandler.class)
```
Defaults *FormatterHandlers* are :
- JsonFormatterHandler which format a message into a JSON
- StringFormatterHandler which format a message into a simple String

Defaults *PersistingHandlers* are :
- FilePersistingHandler which persist a message in a file
- RabbitMQPersistingHandler which persist a message to a RabbitMQ
- SystemOutPersistingHandler which persist a message with the application system out
## 4. Trace method invocation with annotations
Trace annotations are annotation to trace before, after or around method invocation.
You can specify for each method which *FormatterHandler* and/or *PersistingHandler* you want to use

Here are the available annotation :
- TraceBefore, this annotation trace a method before his invocation with his parameters
- TraceAround, this annotation trace a method around his invocation with his parameters and his result
- TraceAfter, this annotation trace a method before his invocation with his parameters
- TraceAfterReturning, this annotation trace a method after returning the response with his parameters and his result
- TraceAfterThrowing, this annotation trace a method after throwing an exception with his parameters and the exception
```
@TraceBefore(persistingHandlerClass = FilePersistingHandler.class)
public Object myMethodToTrace(Object param1, String param2, int param2) {
    ...
}
```
## 5. Change a trace method invocation during application runtime with annotations
To make a method Traceable in order to modify his logging rules, you can place the annotation *@Traceable* on it
```
@Traceable
public Object myMethodToTrace(Object param1, String param2, int param2) {
    ...
}
```
Once its done, with the default config the logger will create a file named rules.csv. In the file, you can register all logging rules of methods with the *@Traceable* annotation
```
# rules.csv

mypackage.MyClass,myMethod,BEFORE,com.github.ffcfalcos.logger.FilePersistingHandler,com.github.ffcfalcos.logger.StringFormatterHandler,false
```
If the example above, we are defining that the method *myMethod* in the class *mypackage.MyClass* will be trace *BEFORE* his executing and the log will be formatted by the *StringFormatterHandler* without his context (*false*) and persisted with the *FilePersistingHandler*

By default, the AbstractRuleLoader (the object reloading modified rules) used is the *FileWatcherRulesLoader* which means that when the file *rules.csv* is saved, logging rules are immediately reloaded

If you want to use an other *RulesStorageHandler* you can create your own and implements *RulesStorageHandler*.
>*RulesStorageHandler* class must have a non-argument constructor
>*AbstractRulesLoader* class must have a constructor with a *RulesStorageHandler* in parameter
>*FileWatcherRulesLoader* works only with *RulesStorageHandler* implementing *FileRulesStorageHandler*

Then to plug it, create a the *logger-manager.xml* file as follow
```
<?xml version="1.0"?>
<logger>
    <traceable>
        <loader-class>com.github.ffcfalcos.logger.trace.FileWatcherRulesLoader</loader-class>
        <storage-handler-class>com.github.ffcfalcos.logger.trace.CsvRulesStorageHandler</storage-handler-class>
    </traceable>
</logger>
```
>This file can be place any where in you project folder

In the example above replace the *storage-handler-class* object by you own *RulesStorageHandler*
and the *loader-class* by the *SleepingRulesLoader* or by your own rules loader (make sur that your respect all points cited in remarks above)

