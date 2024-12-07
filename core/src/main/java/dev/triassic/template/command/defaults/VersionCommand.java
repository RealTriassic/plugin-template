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

package dev.triassic.template.command.defaults;

import dev.triassic.template.command.Commander;
import dev.triassic.template.command.TemplateCommand;
import dev.triassic.template.util.MessageProvider;
import dev.triassic.template.util.UpdateChecker;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;

/**
 * A command that displays the plugin's version.
 */
public class VersionCommand extends TemplateCommand {

    /**
     * Constructs a new {@link VersionCommand} instance.
     */
    public VersionCommand() {
        super("version", "command.version.description", "template.command.version");
    }

    @Override
    public @NonNull List<String> aliases() {
        return List.of("about", "ver");
    }

    @Override
    public void execute(final @NonNull CommandContext<Commander> commandContext) {
        final Commander sender = commandContext.sender();
        UpdateChecker
            .checkForUpdates("RealTriassic/plugin-template", "1.0.0")
            .thenAccept(
                result -> sender.sendMessage(
                    Component.text(MessageProvider.translate(result.getMessage()))
                ));
    }
}
