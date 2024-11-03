package dev.triassic.template.common;

public interface TemplateLogger {

    /**
     * Logs a severe message.
     *
     * @param message the message to log
     */
    void severe(String message);

    /**
     * Logs a severe message along with an exception.
     *
     * @param message the message to log
     * @param error   the exception to log
     */
    void severe(String message, Throwable error);

    /**
     * Logs an error message.
     *
     * @param message the message to log
     */
    void error(String message);

    /**
     * Logs an error message along with an exception.
     *
     * @param message the message to log
     * @param error   the exception to log
     */
    void error(String message, Throwable error);

    /**
     * Logs a warning message.
     *
     * @param message the message to log
     */
    void warning(String message);

    /**
     * Logs an informational message.
     *
     * @param message the message to log
     */
    void info(String message);
}
