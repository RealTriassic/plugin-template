/*
 * MIT License
 *
 * Copyright (c) 2024 Triassic
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.triassic.template;

/**
 * Interface for wrapping platform-specific logger implementations.
 * Provides a unified API for logging across different platforms.
 */
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
