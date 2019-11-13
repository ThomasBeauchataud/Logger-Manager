import com.github.ffcfalcos.logger.Logger;
import com.github.ffcfalcos.logger.LoggerInterface;
import com.github.ffcfalcos.logger.handler.persisting.FilePersistingHandlerInterface;
import com.github.ffcfalcos.logger.interceptor.TraceAround;
import com.github.ffcfalcos.logger.interceptor.TraceBefore;
import com.github.ffcfalcos.logger.interceptor.TraceableInterface;

import java.io.*;

public class TraceInterceptorTest implements TestInterface, TraceableInterface {

    private final LoggerInterface logger = new Logger();
    private final String filePath = System.getProperty("user.dir") + "/src/test/resources/logs.log";

    @TraceAround(persistingHandlerName = "FilePersistingHandler", formatterHandlerName = "StringFormatterHandler")
    private String traceAround(String firstParameter, String secondParameter) {
        return firstParameter + secondParameter;
    }

    @TraceBefore(persistingHandlerName = "FilePersistingHandler", formatterHandlerName = "StringFormatterHandler")
    private String traceBefore(String firstParameter, String secondParameter) {
        return firstParameter + secondParameter;
    }

    @Override
    public void run() throws Exception {
        emptyingTestFile();
        try {
            ((FilePersistingHandlerInterface) logger.getPersistingHandler("FilePersistingHandler")).setFilePath(filePath);
            String secondParameter = "secondParameter";
            String firstParameter = "firstParameter";
            String result = traceBefore(firstParameter, secondParameter);
            if (!result.equals(firstParameter + secondParameter)) {
                System.out.println("TraceInterceptor failed the non invasive test with @TraceBefore");
                throw new Exception("");
            }
            File file = new File(filePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(firstParameter) && line.contains(secondParameter)) {
                    break;
                }
            }
            if (line == null) {
                System.out.println("TraceInterceptor failed the traceBefore test\n");
            }
            bufferedReader.close();
            result = traceAround(firstParameter, secondParameter);
            if (!result.equals(firstParameter + secondParameter)) {
                System.out.println("TraceInterceptor failed the non invasive test with @TraceAround");
                throw new Exception("");
            }
            bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(firstParameter) && line.contains(secondParameter) && line.contains(firstParameter + secondParameter)) {
                    break;
                }
            }
            if (line == null) {
                System.out.println("TraceInterceptor failed  the traceAround test");
            }
            System.out.println("TraceInterceptor succeed the test\n");
        } catch (Exception e) {
            System.out.println("TraceInterceptor failed the test\n");
        }
    }

    @Override
    public LoggerInterface getLogger() {
        return logger;
    }

    private void emptyingTestFile() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        bufferedWriter.write("FirstLine");
        bufferedWriter.close();
    }

}
