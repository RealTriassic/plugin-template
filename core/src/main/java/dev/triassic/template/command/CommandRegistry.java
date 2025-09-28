/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.command;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.annotation.PlatformSpecific;
import dev.triassic.template.command.defaults.ReloadCommand;
import dev.triassic.template.util.PlatformType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.incendo.cloud.CommandManager;

/**
 * A centralized registry for managing and registering commands in the plugin.
 */
@RequiredArgsConstructor
public final class CommandRegistry {

    private final TemplateImpl instance;
    private final CommandManager<Commander> commandManager;

    /**
     * Registers all commands with the command manager.
     */
    public void registerAll(PlatformType platform) {
        final List<TemplateCommand> commands = List.of(
            new ReloadCommand(instance)
        );

        commands.forEach(command -> {
            if (isSupported(command.getClass(), platform)) {
                command.register(commandManager);
            } else {
                System.out.println("Skipping " + command.getClass().getSimpleName()
                    + " (not supported on " + platform + ")");
            }
        });
    }

    private static boolean isSupported(Class<?> clazz, PlatformType platform) {
        PlatformSpecific annotation = clazz.getAnnotation(PlatformSpecific.class);
        if (annotation == null) {
            return true;
        }
        for (PlatformType allowed : annotation.value()) {
            if (allowed == platform) {
                return true;
            }
        }
        return false;
    }
}
