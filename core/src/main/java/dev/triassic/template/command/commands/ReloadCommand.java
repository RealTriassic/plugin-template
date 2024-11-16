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

package dev.triassic.template.command.commands;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.command.CommandSource;
import dev.triassic.template.command.TemplateCommand;
import dev.triassic.template.util.MessageProvider;
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
