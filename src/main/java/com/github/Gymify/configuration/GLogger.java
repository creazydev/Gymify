package com.github.Gymify.configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface GLogger {
    final static Logger LOGGER = Logger.getLogger(GLogger.class.getCanonicalName());

    default void debug(String str) {
        LOGGER.log(Level.ALL, str);
    }
}
