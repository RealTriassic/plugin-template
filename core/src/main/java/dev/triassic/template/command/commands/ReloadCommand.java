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

import static org.incendo.cloud.minecraft.extras.RichDescription.richDescription;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.command.Commander;
import dev.triassic.template.command.TemplateCommand;
import dev.triassic.template.util.MessageProvider;
import java.util.Locale;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.bean.CommandBean;
import org.incendo.cloud.bean.CommandProperties;
import org.incendo.cloud.context.CommandContext;

/**
 * A command that reloads the application's configuration.
 */
public class ReloadCommand extends CommandBean<Commander> implements TemplateCommand {

    private TemplateImpl instance;

    @Override
    public void register(
        @NonNull TemplateImpl instance,
        @NonNull CommandManager<Commander> commandManager
    ) {
        this.instance = instance;
        commandManager.command(this);
    }

    @Override
    protected @NonNull CommandProperties properties() {
        return CommandProperties.of("reload");
    }

    @Override
    protected Command.@NonNull Builder<? extends Commander> configure(
        Command.@NonNull Builder<Commander> builder
    ) {
        return builder
            .senderType(Commander.class)
            .permission("template.command.reload")
            .commandDescription(richDescription(
                Component.text(MessageProvider.translate("reloadCommandDescription"))
            ));
    }

    @Override
    public void execute(final @NonNull CommandContext<Commander> commandContext) {
        final Commander sender = commandContext.sender();
        instance.getConfig().reload().handleAsync((v, ex) -> {
            if (ex == null) {
                sender.sendMessage(Component.text(
                    MessageProvider.translate(
                        "reloadCommandSuccess", Locale.ENGLISH), NamedTextColor.GREEN)
                );
            } else {
                sender.sendMessage(Component.text(
                    MessageProvider.translate(
                        "reloadCommandFailure", Locale.ENGLISH), NamedTextColor.RED)
                );
                instance.getLogger().error(ex.getMessage(), ex.getCause());
            }
            return null;
        });
    }
}
