package ru.makhach.utils;

import java.time.LocalDateTime;

/**
 * Логгер со своей логикой вывода информации
 * Синглтон
 */
public class Logger {
    private static final Logger logger;

    static {
        logger = new Logger();
    }

    private Logger() {
    }

    public static Logger getInstance() {
        return logger;
    }

    public void error(Exception exception) {
        this.log(exception.getLocalizedMessage());
    }

    public void log(String message) {
        System.out.println(String.format("%s %s", LocalDateTime.now(), message));
    }
}
