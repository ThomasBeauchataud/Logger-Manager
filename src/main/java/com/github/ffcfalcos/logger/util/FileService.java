package com.github.ffcfalcos.logger.util;

import java.io.File;
import java.util.Objects;
import java.util.Stack;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * File service class
 */
public class FileService {

    /**
     * Create folders for the file path if necessary
     *
     * @param filePath String
     */
    public static void createFilePath(String filePath) {
        try {
            new File(filePath).createNewFile();
        } catch (Exception e) {
            Stack<String> stack = new Stack<>();
            stack.push(filePath.substring(0, filePath.lastIndexOf('/')));
            createFolderPath(stack);
            try {
                new File(filePath).createNewFile();
            } catch (Exception ignored) { }
        }
    }

    /**
     * Return the logger config file if existing or null
     *
     * @return File | null
     */
    public static File getConfigFile() {
        return getFileIfExistingInternal(new File(System.getProperty("user.dir")), "logger-manager.xml");
    }

    /**
     * Look for a file in any project directory
     *
     * @param directory File
     * @param fileName  String
     * @return File | null
     */
    private static File getFileIfExistingInternal(File directory, String fileName) {
        if (directory.isFile() && directory.getName().equals(fileName)) {
            return directory;
        }
        if (directory.isDirectory()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                File fileTest = getFileIfExistingInternal(file, fileName);
                if (fileTest != null) {
                    return file;
                }
            }
        }
        return null;
    }

    /**
     * Create folders recursively
     *
     * @param stack Stack
     */
    private static void createFolderPath(Stack<String> stack) {
        while (!stack.empty()) {
            if (new File(stack.peek()).mkdir()) {
                stack.pop();
            } else {
                stack.push(stack.peek().substring(0, stack.peek().lastIndexOf('/')));
                createFolderPath(stack);
            }
        }
    }

}
