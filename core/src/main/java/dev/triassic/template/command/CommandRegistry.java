package dev.triassic.template.command;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.command.defaults.ReloadCommand;
import dev.triassic.template.command.defaults.VersionCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.incendo.cloud.CommandManager;

/**
 * A centralized registry for managing and registering commands in the plugin.
 */
@RequiredArgsConstructor
public class CommandRegistry {

    private final TemplateImpl instance;
    private final CommandManager<Commander> commandManager;
    private final List<TemplateCommand> commands = List.of(
        new ReloadCommand(instance),
        new VersionCommand()
    );

    /**
     * Registers all commands with the command manager.
     */
    public void registerAll() {
        commands.forEach(command -> command.register(commandManager));
    }
}
