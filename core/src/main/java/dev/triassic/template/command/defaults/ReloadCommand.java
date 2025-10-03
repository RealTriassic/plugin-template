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
import dev.triassic.template.annotation.ExcludePlatform;
import dev.triassic.template.command.Commander;
import dev.triassic.template.command.TemplateCommand;
import dev.triassic.template.configuration.ConfigurationManager;
import dev.triassic.template.configuration.TemplateConfiguration;
import dev.triassic.template.util.PlatformType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.slf4j.Logger;

/**
 * A command that reloads the plugin's configuration.
 */
@ExcludePlatform({PlatformType.VELOCITY})
public final class ReloadCommand extends TemplateCommand {

    private final Logger logger;
    private final ConfigurationManager<TemplateConfiguration> config;

    /**
     * Constructs a new {@link ReloadCommand} instance.
     */
    public ReloadCommand(final @NonNull TemplateImpl instance) {
        super("reload", "Reloads the plugin configuration.", "template.command.reload");
        this.logger = instance.getLogger();
        this.config = instance.getConfig();
    }

    @Override
    public void execute(final @NonNull CommandContext<Commander> commandContext) {
        final Commander sender = commandContext.sender();
        config.reload().handleAsync((v, ex) -> {
            if (ex == null) {
                sender.sendMessage(Component.text(
                    "TemplatePlugin configuration has been reloaded.", NamedTextColor.GREEN)
                );
            } else {
                sender.sendMessage(Component.text(
                    "Failed to reload TemplatePlugin configuration, check console for details.",
                    NamedTextColor.RED)
                );
                logger.error(ex.getMessage(), ex.getCause());
            }
            return null;
        });
    }
}
