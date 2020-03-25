package com.github.ffcfalcos.logger;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Managing Rules entities by storing, getting and removing them in any types of files
 * We have to be able to create, modify and remove rules during the application runtime
 */
public interface FileRulesStorageHandler extends RulesStorageHandler {

    /**
     * Return the actual file path
     * @return String
     */
    String getFilePath();

}
