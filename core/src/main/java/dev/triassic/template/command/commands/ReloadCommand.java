package dev.triassic.template.command.commands;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.command.CommandSource;
import dev.triassic.template.command.TemplateCommand;
import dev.triassic.template.localization.MessageProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;

import java.util.Locale;

public class ReloadCommand extends TemplateCommand {

    public ReloadCommand(
            final TemplateImpl instance,
            final CommandManager<CommandSource> commandManager
    ) {
        super(instance, commandManager, "reload", Component.text(MessageProvider.get("reloadCommandDescription", Locale.ENGLISH)));
    }

    @Override
    protected void execute(CommandContext<CommandSource> ctx) {
        instance.getConfig().reload().handleAsync((v, ex) -> {
            if (ex == null) {
                ctx.sender().sendMessage(Component.text(MessageProvider.get("reloadCommandSuccess", Locale.ENGLISH), NamedTextColor.GREEN));
            } else {
                ctx.sender().sendMessage(Component.text(MessageProvider.get("reloadCommandFailure", Locale.ENGLISH), NamedTextColor.RED));
                instance.getLogger().error(ex.getMessage(), ex.getCause());
            }
            return null;
        });
    }
}
