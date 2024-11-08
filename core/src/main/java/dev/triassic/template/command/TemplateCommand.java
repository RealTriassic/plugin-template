package dev.triassic.template.command;

import dev.triassic.template.TemplateImpl;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;

import static org.incendo.cloud.minecraft.extras.RichDescription.richDescription;

public abstract class TemplateCommand {

    protected TemplateImpl instance;

    public TemplateCommand(
            final TemplateImpl instance,
            final CommandManager<Commander> commandManager,
            final String name,
            final Component description
    ) {
        this.instance = instance;
        var command = commandManager.commandBuilder("template")
                .literal(name)
                .senderType(Commander.class)
                .permission("template.command." + name)
                .commandDescription(richDescription(description))
                .handler(this::execute)
                .build();

        commandManager.command(command);
    }

    protected abstract void execute(CommandContext<Commander> ctx);
}
