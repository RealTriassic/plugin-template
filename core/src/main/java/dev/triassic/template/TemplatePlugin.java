/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
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
