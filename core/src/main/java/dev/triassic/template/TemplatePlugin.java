/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <https://unlicense.org/>
 */

package dev.triassic.template;

import dev.triassic.template.command.Commander;
import dev.triassic.template.util.PlatformType;
import java.nio.file.Path;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.incendo.cloud.CommandManager;
import org.slf4j.Logger;

/**
 * Interface for platform-specific logic.
 *
 * <p>All platforms must implement this interface to provide platform-specific functionality.</p>
 */
@DefaultQualifier(NonNull.class)
public interface TemplatePlugin {

    /**
     * Gets the {@link Logger} for this platform.
     *
     * @return the slf4j logger
     */
    Logger logger();

    /**
     * Gets the path to the platform's data directory.
     *
     * @return the path to the data directory
     */
    Path dataDirectory();

    /**
     * Gets the {@link PlatformType} for this platform.
     *
     * @return the platform type
     */
    PlatformType platformType();

    /**
     * Gets the {@link CommandManager} used to manage commands for this platform.
     *
     * @return the command manager
     */
    CommandManager<Commander> commandManager();
}
