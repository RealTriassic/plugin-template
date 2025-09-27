/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.command;

import static org.incendo.cloud.description.CommandDescription.commandDescription;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;

/**
 * Abstract class that represents a command in the plugin.
 */
@Getter
@RequiredArgsConstructor
public abstract class TemplateCommand {

    private static final String ROOT_COMMAND = "template";

    private final String name;
    private final String description;
    private final String permission;

    /**
     * Provides a list of aliases for this command.
     * Default is an empty list.
     *
     * @return a list of aliases for this command
     */
    @NonNull
    protected List<String> aliases() {
        return List.of();
    }

    /**
     * Configures the command's builder with properties
     * such as its name, aliases, sender type, and description.
     *
     * @param manager the {@link CommandManager} instance
     * @return the configured {@link Command.Builder}
     */
    protected final Command.Builder<Commander> configure(
        final @NonNull CommandManager<Commander> manager) {
        return manager.commandBuilder(ROOT_COMMAND)
            .literal(name, aliases().toArray(new String[0]))
            .senderType(Commander.class)
            .permission(permission)
            .commandDescription(commandDescription(description));
    }

    /**
     * Registers the command with the provided {@link CommandManager}.
     *
     * @param manager the {@link CommandManager} to register the command with
     */
    public void register(final @NonNull CommandManager<Commander> manager) {
        manager.command(configure(manager).handler(this::execute));
    }

    /**
     * Executes the command when triggered by a sender.
     *
     * @param context the {@link CommandContext} for the command execution
     */
    protected abstract void execute(final @NonNull CommandContext<Commander> context);
}


