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
import dev.triassic.template.command.TemplateCommand;
import dev.triassic.template.command.commands.ReloadCommand;
import dev.triassic.template.configuration.ConfigurationManager;
import dev.triassic.template.configuration.TemplateConfiguration;
import dev.triassic.template.localization.LocalizationCache;
import dev.triassic.template.util.MessageProvider;
import dev.triassic.template.util.PlatformType;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.incendo.cloud.CommandManager;

/**
 * Handles platform-specific data, configuration, localization, and logging.
 * Serves as the main class for initializing and managing the platform.
 */
@Getter
public class TemplateImpl {

    private static final List<TemplateCommand> commands = Arrays.asList(
        new ReloadCommand()
    );

    private final Path dataFolder;
    private final TemplateLogger logger;
    private final PlatformType platformType;
    private final LocalizationCache localizationCache;
    private final CommandManager<Commander> commandManager;

    private ConfigurationManager<TemplateConfiguration> config;

    /**
     * Initializes a new {@link TemplateImpl} instance.
     *
     * @param bootstrap    platform-specific {@link TemplateBootstrap} instance
     * @param platformType the type of {@link PlatformType}
     */
    public TemplateImpl(
            final TemplateBootstrap bootstrap,
            final PlatformType platformType
    ) {
        final long startTime = System.currentTimeMillis();

        this.dataFolder = bootstrap.dataDirectory();
        this.logger = bootstrap.templateLogger();
        this.platformType = platformType;

        this.localizationCache = new LocalizationCache(this);
        MessageProvider.setLocalizationCache(localizationCache);

        this.commandManager = bootstrap.commandManager();

        commands.forEach(command -> command.register(this, commandManager));

        try {
            this.config = ConfigurationManager.load(dataFolder, TemplateConfiguration.class);
        } catch (IOException e) {
            logger.error(MessageProvider.translate("template.config.load.fail"), e);
            return;
        }

        logger.info(MessageProvider.translate(
            "template.enabled", System.currentTimeMillis() - startTime));
    }
}
