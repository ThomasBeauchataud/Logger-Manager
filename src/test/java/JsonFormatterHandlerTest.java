import com.github.ffcfalcos.logger.collector.Severity;
import com.github.ffcfalcos.logger.handler.formatter.FormatterHandler;
import com.github.ffcfalcos.logger.handler.formatter.JsonFormatterHandler;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonFormatterHandlerTest implements TestInterface {

    private final FormatterHandler jsonFormatter = new JsonFormatterHandler();

    @Override
    public void run() {
        try {
            try {
                String nullString = null;
                jsonFormatter.format(nullString, Severity.INFO);
            } catch (Exception e) {
                System.out.println("JsonFormatterHandler failed null string test");
                throw e;
            }
            try {
                Map<String, Object> nullMap = null;
                jsonFormatter.format(nullMap);
            } catch (Exception e) {
                System.out.println("JsonFormatterHandler failed null map test");
                throw e;
            }
            try {
                String string = "String message";
                String formattedString = jsonFormatter.format(string, Severity.INFO);
                if (isNoneValidJson(formattedString)) {
                    throw new Exception("");
                }
            } catch (Exception e) {
                System.out.println("JsonFormatterHandler failed the string formatter test");
                throw e;
            }
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("stringKey", "string");
                map.put("nullKey", null);
                map.put("objectKey", new ArrayList<>());
                String formattedJson = jsonFormatter.format(map);
                if (isNoneValidJson(formattedJson)) {
                    throw new Exception("");
                }
            } catch (Exception e) {
                System.out.println("JsonFormatterHandler failed the map formatter test");
                throw e;
            }
            System.out.println("JsonFormatterHandler succeed the test\n");
        } catch (Exception e) {
            System.out.println("JsonFormatterHandler failed test\n");
        }
    }

    private boolean isNoneValidJson(String test) {
        try {
            new JSONParser().parse(test);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

}
