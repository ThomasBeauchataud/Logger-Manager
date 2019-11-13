import java.util.ArrayList;
import java.util.List;

public class Main {

    private List<TestInterface> loadTests() {
        List<TestInterface> tests = new ArrayList<>();
        tests.add(new JsonFormatterHandlerTest());
        tests.add(new FilePersistingHandlerTest());
        tests.add(new TraceInterceptorTest());
        tests.add(new StringFormatterHandlerTest());
        return tests;
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        for(TestInterface test : main.loadTests()) {
            test.run();
        }
    }

}
