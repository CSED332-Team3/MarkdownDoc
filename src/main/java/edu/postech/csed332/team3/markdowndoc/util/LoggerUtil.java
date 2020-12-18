package edu.postech.csed332.team3.markdowndoc.util;

import java.util.logging.Logger;

/**
 * Logging utility aid.
 */
public class LoggerUtil {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private LoggerUtil() {
    }

    public static void warning(String msg) {
        LOGGER.warning(msg);
    }

}
