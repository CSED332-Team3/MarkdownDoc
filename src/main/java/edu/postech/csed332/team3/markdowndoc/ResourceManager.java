package edu.postech.csed332.team3.markdowndoc;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Utility class for getting files from the Resources package
 */
public class ResourceManager {

    /**
     * Get the input stream of file
     *
     * @param fileName the file name
     * @return the input stream
     * @throws IllegalArgumentException file doesn't exist
     */
    public static InputStream getFileFromResourceAsStream(String fileName) throws FileNotFoundException {

        // The class loader that loaded the class
        ClassLoader classLoader = ResourceManager.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: " + fileName);
        } else {
            return inputStream;
        }

    }
}
