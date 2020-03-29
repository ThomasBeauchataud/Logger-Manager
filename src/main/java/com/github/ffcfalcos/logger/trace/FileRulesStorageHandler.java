package com.github.ffcfalcos.logger.trace;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Managing Rules entities by storing, getting and removing them in any types of files
 * We have to be able to create, modify and remove rules during the application runtime
 */
public interface FileRulesStorageHandler extends RulesStorageHandler {

    /**
     * Return the actual file path
     *
     * @return String
     */
    String getFilePath();

    /**
     * Change the file path of the FileRulesStorageHandler
     * Set migration to true to migration rules stored on the previous file
     *
     * @param filePath String
     * @param migration boolean
     */
    void setFilePath(String filePath, boolean migration);
}
