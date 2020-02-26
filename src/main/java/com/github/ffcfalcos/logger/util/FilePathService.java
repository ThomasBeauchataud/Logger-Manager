package com.github.ffcfalcos.logger.util;

import java.io.File;
import java.util.Stack;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * File path service class
 */
public class FilePathService {

    /**
     * Create folders for the file path if necessary
     * @param filePath String
     */
    public static void checkFilePath(String filePath) {
        try {
            new File(filePath).createNewFile();
        } catch (Exception e) {
            Stack<String> stack = new Stack<>();
            stack.push(filePath.substring(0, filePath.lastIndexOf('/')));
            createFolderPath(stack);
        }
    }

    /**
     * Create folders recursively
     * @param stack Stack
     */
    private static void createFolderPath(Stack<String> stack) {
        while(!stack.empty()) {
            if(new File(stack.peek()).mkdir()) {
                stack.pop();
            } else {
                stack.push(stack.peek().substring(0, stack.peek().lastIndexOf('/')));
                createFolderPath(stack);
            }
        }
    }

}
