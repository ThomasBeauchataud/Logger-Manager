package com.github.ffcfalcos.logger.trace;

import com.github.ffcfalcos.logger.trace.AbstractRulesLoader;
import com.github.ffcfalcos.logger.trace.RulesStorageHandler;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Thread that load rules from the RuleStorageHandler and inject them in the
 *      AbstractTraceableAnnotationHandler rules list
 * This thread is working with an infinite loop sleeping each 15 minutes
 */
@SuppressWarnings({"InfiniteLoopStatement","unused"})
public class SleepRulesLoader extends AbstractRulesLoader implements Runnable {

    /**
     * SleepRulesLoader Constructor
     * @param rulesStorageHandler RulesStorageHandler
     */
    public SleepRulesLoader(RulesStorageHandler rulesStorageHandler) {
        super(rulesStorageHandler);
    }

    /**
     * Regenerate Rule list for the AbstractTraceableAnnotationHandler
     */
    @Override
    public void run() {
        try {
            while (true) {
                rules.clear();
                rules.addAll(rulesStorageHandler.getRules());
                Thread.sleep(900000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
