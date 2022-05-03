package com.github.Gymify.configuration;

import java.util.logging.Logger;

public interface GLogger {
    Logger LOGGER = Logger.getLogger(GLogger.class.getCanonicalName());

    default void debug(String str) {
        LOGGER.info(str);
    }
}
