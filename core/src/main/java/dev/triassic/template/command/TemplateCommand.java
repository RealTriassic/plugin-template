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

package dev.triassic.template.command;

import static org.incendo.cloud.description.CommandDescription.commandDescription;

import dev.triassic.template.util.MessageProvider;
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
            .commandDescription(commandDescription(MessageProvider.translate(description)));
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


