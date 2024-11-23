package dev.triassic.template.command;

import dev.triassic.template.TemplateImpl;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;

/**
 * Represents a command in the application.
 */
public interface TemplateCommand {

    /**
     * Registers the command with the specified {@link CommandManager}.
     *
     * @param instance       the {@link TemplateImpl} instance
     * @param commandManager the {@link CommandManager} to register the command with
     */
    void register(
        final @NonNull TemplateImpl instance,
        final @NonNull CommandManager<Commander> commandManager
    );
}
