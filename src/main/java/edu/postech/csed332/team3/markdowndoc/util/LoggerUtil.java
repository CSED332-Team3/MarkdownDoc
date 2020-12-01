package edu.postech.csed332.team3.markdowndoc.util;

import java.util.logging.Logger;

/**
 * Logging utility aid.
 */
public class LoggerUtil {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private LoggerUtil() {
    }

    public static void severe(String msg) {
        LOGGER.severe(msg);
    }

    public static void warning(String msg) {
        LOGGER.warning(msg);
    }

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void config(String msg) {
        LOGGER.config(msg);
    }

    public static void fine(String msg) {
        LOGGER.fine(msg);
    }

    public static void finer(String msg) {
        LOGGER.finer(msg);
    }

    public static void finest(String msg) {
        LOGGER.finest(msg);
    }
}
