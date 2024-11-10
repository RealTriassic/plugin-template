/*
 * MIT License
 *
 * Copyright (c) 2024 Triassic
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.triassic.template.command.commands;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.command.CommandSource;
import dev.triassic.template.command.TemplateCommand;
import dev.triassic.template.localization.MessageProvider;
import java.util.Locale;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;

/**
 * The reload command, reloads the application and it's configuration.
 */
public class ReloadCommand extends TemplateCommand {

    /**
     * Initializes a new {@link ReloadCommand} instance.
     *
     * @param instance the {@link TemplateImpl} instance
     * @param commandManager the {@link CommandManager} to register the command with
     */
    public ReloadCommand(
            final TemplateImpl instance,
            final CommandManager<CommandSource> commandManager
    ) {
        super(instance, commandManager, "reload",
            Component.text(MessageProvider.translate("reloadCommandDescription", Locale.ENGLISH)));
    }

    @Override
    protected void execute(CommandContext<CommandSource> ctx) {
        instance.getConfig().reload().handleAsync((v, ex) -> {
            if (ex == null) {
                ctx.sender().sendMessage(Component.text(
                    MessageProvider.translate(
                        "reloadCommandSuccess", Locale.ENGLISH), NamedTextColor.GREEN)
                );
            } else {
                ctx.sender().sendMessage(Component.text(
                    MessageProvider.translate(
                        "reloadCommandFailure", Locale.ENGLISH), NamedTextColor.RED)
                );
                instance.getLogger().error(ex.getMessage(), ex.getCause());
            }
            return null;
        });
    }
}
