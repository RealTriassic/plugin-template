/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template;

import dev.triassic.template.command.CommandRegistry;
import dev.triassic.template.command.Commander;
import dev.triassic.template.configuration.ConfigurationManager;
import dev.triassic.template.configuration.TemplateConfiguration;
import dev.triassic.template.util.PlatformType;
import java.io.IOException;
import java.nio.file.Path;
import lombok.Getter;
import org.incendo.cloud.CommandManager;
import org.slf4j.Logger;

/**
 * The main entry point for initializing and managing the platform implementation.
 */
@Getter
public final class TemplateImpl {

    private final Logger logger;
    private final Path dataDirectory;
    private final PlatformType platformType;
    private final CommandManager<Commander> commandManager;

    private CommandRegistry commandRegistry;
    private ConfigurationManager<TemplateConfiguration> config;

    /**
     * Initializes a new {@link TemplateImpl} instance.
     *
     * @param plugin platform-specific {@link TemplatePlugin} instance
     */
    public TemplateImpl(final TemplatePlugin plugin) {
        this.logger = plugin.logger();
        this.dataDirectory = plugin.dataDirectory();
        this.platformType = plugin.platformType();
        this.commandManager = plugin.commandManager();
    }

    /**
     * Called when the bootstrapped plugin is done initializing.
     */
    public void initialize() {
        final long startTime = System.currentTimeMillis();

        try {
            this.config = ConfigurationManager.load(
                dataDirectory, TemplateConfiguration.class, platformType);
        } catch (IOException e) {
            logger.error("Failed to load configuration", e);
            return;
        }

        this.commandRegistry = new CommandRegistry(this, commandManager);
        commandRegistry.registerAll(platformType);

        logger.info("Enabled on {} in {}ms",
            getPlatformType(), System.currentTimeMillis() - startTime);
    }

    /**
     * Called when the bootstrapped plugin is shutting down.
     */
    public void shutdown() {
        logger.info("Goodbye!");
    }
}
