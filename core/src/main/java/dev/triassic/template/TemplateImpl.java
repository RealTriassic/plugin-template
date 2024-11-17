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

import dev.triassic.template.command.CommandSource;
import dev.triassic.template.configuration.ConfigurationManager;
import dev.triassic.template.configuration.TemplateConfiguration;
import dev.triassic.template.localization.LocalizationCache;
import dev.triassic.template.util.MessageProvider;
import dev.triassic.template.util.PlatformType;
import java.io.IOException;
import java.nio.file.Path;
import lombok.Getter;
import org.incendo.cloud.CommandManager;

/**
 * The main class responsible for managing platform-specific data, configuration,
 * localization, and logging.
 */
@Getter
public class TemplateImpl {

    private final PlatformType platformType;

    private final Path dataFolder;
    private final TemplateLogger logger;

    private ConfigurationManager<TemplateConfiguration> config;
    private LocalizationCache localizationCache;
    private CommandManager<CommandSource> commandManager;

    /**
     * Initializes a new {@link TemplateImpl} instance.
     *
     * @param platformType the type of platform
     * @param bootstrap    platform-specific bootstrap instance
     */
    public TemplateImpl(
            final PlatformType platformType,
            final TemplateBootstrap bootstrap
    ) {
        final long startTime = System.currentTimeMillis();

        this.platformType = platformType;

        this.dataFolder = bootstrap.dataDirectory();
        this.logger = bootstrap.templateLogger();

        try {
            this.config = ConfigurationManager.load(dataFolder, TemplateConfiguration.class);
        } catch (IOException e) {
            logger.error("Failed to load configuration", e);
            return;
        }

        this.localizationCache = new LocalizationCache(this);
        MessageProvider.setLocalizationCache(localizationCache);

        this.commandManager = bootstrap.commandManager();

        logger.info("Enabled in " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
