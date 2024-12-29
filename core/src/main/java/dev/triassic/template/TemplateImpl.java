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
            this.config = ConfigurationManager.load(dataDirectory, TemplateConfiguration.class);
        } catch (IOException e) {
            logger.error("Failed to load configuration", e);
            return;
        }

        this.commandRegistry = new CommandRegistry(this, commandManager);
        commandRegistry.registerAll();

        logger.info("Enabled in {}ms", System.currentTimeMillis() - startTime);
    }

    /**
     * Called when the bootstrapped plugin is shutting down.
     */
    public void shutdown() {
        logger.info("Goodbye!");
    }
}
