package com.github.ffcfalcos.logger;

import com.github.ffcfalcos.logger.trace.CsvRulesStorageHandler;
import com.github.ffcfalcos.logger.trace.RulesStorageHandler;
import com.github.ffcfalcos.logger.trace.Entry;
import com.github.ffcfalcos.logger.trace.Rule;
import com.github.ffcfalcos.logger.util.FilePathService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RulesStorageHandlerTest {

    @Test
    public void run() {
        final String filePath = System.getProperty("user.dir") + "/" + UUID.randomUUID().toString() + "rules.csv";
        CsvRulesStorageHandler csvRulesStorageHandler = new CsvRulesStorageHandler();
        csvRulesStorageHandler.setFilePath(filePath);
        runInternal(csvRulesStorageHandler);
        FilePathService.deleteFile(filePath);
    }

    public void runInternal(RulesStorageHandler rulesStorageHandler) {
        Rule rule1 = new Rule("class1", "method1", Entry.AFTER, null, null);
        Rule rule2 = new Rule("class2", "method2", Entry.BEFORE, FilePersistingHandler.class, null);
        Rule rule3 = new Rule("class3", "method3", Entry.AROUND, null, JsonFormatterHandler.class);
        Rule rule4 = new Rule("class4", "method4", Entry.AFTER_THROWING, null, JsonFormatterHandler.class);
        Rule rule5 = new Rule("class5", "method5", Entry.AFTER_RETURNING, null, JsonFormatterHandler.class);
        Rule rule6 = new Rule("class6", "method6", Entry.AFTER_RETURNING, null, JsonFormatterHandler.class);
        assertDoesNotThrow(() -> rulesStorageHandler.addRules(Arrays.asList(rule1, rule2, rule3, rule4)),
                "Trying to add rules");
        assertDoesNotThrow(() -> rulesStorageHandler.removeRules(Arrays.asList(rule1, rule2)),
                "Trying to remove rules");
        assertDoesNotThrow(rulesStorageHandler::getRules,
                "Trying to get rules");
        assert(rulesStorageHandler.getRules().size() == 2);
        assertDoesNotThrow(() -> rulesStorageHandler.removeRules(Arrays.asList(rule5, rule6)),
                "Trying to remove not existing rules");
        assertDoesNotThrow(() -> rulesStorageHandler.removeRules(null),
                "Trying to remove null object");
        assertDoesNotThrow(() -> rulesStorageHandler.addRules(null),
                "Trying to add null object");
    }

}
