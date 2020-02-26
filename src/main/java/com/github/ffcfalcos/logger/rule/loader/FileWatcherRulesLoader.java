package com.github.ffcfalcos.logger.rule.loader;

import com.github.ffcfalcos.logger.rule.storage.FileRulesStorageHandler;

import java.nio.file.*;
import java.util.List;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Thread that load rules from the RuleStorageHandler and inject them in the
 *      AbstractTraceableAnnotationHandler rules list
 * This thread is working with an infinite loop triggering with a file watcher
 */
@SuppressWarnings({"unused","InfiniteLoopStatement"})
public class FileWatcherRulesLoader extends AbstractRulesLoader {

    private WatchService watcher;
    private String filePath;

    /**
     * AbstractRulesLoader Constructor
     * @param fileRulesStorageHandler FileRulesStorageHandler
     */
    public FileWatcherRulesLoader(FileRulesStorageHandler fileRulesStorageHandler) {
        super(fileRulesStorageHandler);
        filePath = fileRulesStorageHandler.getFilePath();
        try {
            Path path = Paths.get(filePath.substring(0, filePath.lastIndexOf('/')));
            watcher = path.getFileSystem().newWatchService();
            path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Regenerate Rule list for the AbstractTraceableAnnotationHandler
     */
    @Override
    public void run() {
        while (true) {
            try {
                WatchKey watchKey = watcher.take();
                List<WatchEvent<?>> events = watchKey.pollEvents();
                for (WatchEvent<?> event : events) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY && filePath.contains(event.context().toString())) {
                        rules.clear();
                        rules.addAll(rulesStorageHandler.getRules());
                    }
                }
                watchKey.reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
