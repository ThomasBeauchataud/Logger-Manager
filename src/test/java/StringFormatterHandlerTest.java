import com.github.ffcfalcos.logger.handler.formatter.FormatterHandlerInterface;
import com.github.ffcfalcos.logger.handler.formatter.StringFormatterHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StringFormatterHandlerTest implements TestInterface {

    private final FormatterHandlerInterface stringFormatter = new StringFormatterHandler();

    @Override
    public void run() {
        try {
            try {
                String nullString = null;
                stringFormatter.format(nullString);
            } catch (Exception e) {
                System.out.println("StringFormatterHandler failed null string test");
                throw e;
            }
            try {
                Map<String, Object> nullMap = null;
                stringFormatter.format(nullMap);
            } catch (Exception e) {
                System.out.println("StringFormatterHandler failed null map test");
                throw e;
            }
            try {
                String string = "String message";
                String formattedString = stringFormatter.format(string);
                if (formattedString == null) {
                    throw new Exception("");
                }
            } catch (Exception e) {
                System.out.println("StringFormatterHandler failed the string formatter test");
                throw e;
            }
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("stringKey", "string");
                map.put("nullKey", null);
                map.put("objectKey", new ArrayList<>());
                String formattedJson = stringFormatter.format(map);
                if (formattedJson == null) {
                    throw new Exception("");
                }
            } catch (Exception e) {
                System.out.println("StringFormatterHandler failed the map formatter test");
                throw e;
            }
            System.out.println("StringFormatterHandler succeed the test\n");
        } catch (Exception e) {
            System.out.println("StringFormatterHandler failed test\n");
        }
    }

}
