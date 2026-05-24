/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.command.defaults;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.command.Commander;
import dev.triassic.template.command.TemplateCommand;
import dev.triassic.template.configuration.ConfigurationManager;
import dev.triassic.template.configuration.TemplateConfiguration;
import dev.triassic.template.i18n.TranslationKey;
import dev.triassic.template.i18n.TranslationManager;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.slf4j.Logger;

/**
 * A command that reloads both the plugin's configuration and translations.
 */
public final class ReloadCommand extends TemplateCommand {

    private final Logger logger;
    private final ConfigurationManager<TemplateConfiguration> config;
    private final TranslationManager translationManager;

    /**
     * Constructs a new {@link ReloadCommand} instance.
     *
     * @param instance the core implementation instance of the plugin
     */
    public ReloadCommand(final @NonNull TemplateImpl instance) {
        super("reload", "Reloads the plugin configuration and translations.",
            "template.command.reload");
        this.logger = instance.getLogger();
        this.config = instance.getConfig();
        this.translationManager = instance.getTranslationManager();
    }

    @Override
    public void execute(final @NonNull CommandContext<Commander> commandContext) {
        final Commander sender = commandContext.sender();
        config.reload()
            .thenRun(() -> {
                // translationManager.reload(); TODO: fix translation reload race condition.
                sender.sendMessage(TranslationKey.COMMAND_RELOAD_SUCCESS);
            })
            .exceptionally(ex -> {
                sender.sendMessage(TranslationKey.COMMAND_RELOAD_FAIL);
                logger.error("An error occurred whilst trying to reload", ex);
                return null;
            });
    }
}
