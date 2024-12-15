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
import dev.triassic.template.configuration.ConfigurationManager;
import dev.triassic.template.configuration.TemplateConfiguration;
import dev.triassic.template.localization.LocalizationCache;
import dev.triassic.template.util.MessageProvider;
import dev.triassic.template.util.PlatformType;
import dev.triassic.template.util.UpdateChecker;
import java.io.IOException;
import java.nio.file.Path;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles platform-specific data, configuration, localization, and logging.
 * Serves as the main class for initializing and managing the platform.
 */
@Getter
public class TemplateImpl {

    private final Path dataFolder;
    private final PlatformType platformType;
    private final LocalizationCache localizationCache;
    private final CommandRegistry commandRegistry;

    private ConfigurationManager<TemplateConfiguration> config;

    private static final Logger logger = LoggerFactory.getLogger(TemplateImpl.class);

    /**
     * Initializes a new {@link TemplateImpl} instance.
     *
     * @param bootstrap    platform-specific {@link TemplateBootstrap} instance
     */
    public TemplateImpl(final TemplateBootstrap bootstrap) {
        final long startTime = System.currentTimeMillis();

        this.dataFolder = bootstrap.dataDirectory();
        this.platformType = bootstrap.platformType();

        this.localizationCache = new LocalizationCache(this);
        MessageProvider.setLocalizationCache(localizationCache);

        this.commandRegistry = new CommandRegistry(this, bootstrap.commandManager());
        commandRegistry.registerAll();

        try {
            this.config = ConfigurationManager.load(dataFolder, TemplateConfiguration.class);
        } catch (IOException e) {
            logger.error(MessageProvider.translate("template.config.load.fail"), e);
            return;
        }

        UpdateChecker
            .checkForUpdates("RealTriassic/plugin-template", "1.0.0")
            .thenAccept(
                result -> logger.info(MessageProvider.translate(result.getMessage())));

        logger.info(MessageProvider.translate(
            "template.enabled", System.currentTimeMillis() - startTime));
    }
}
