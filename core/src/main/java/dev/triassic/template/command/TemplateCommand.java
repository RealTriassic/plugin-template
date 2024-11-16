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

import static org.incendo.cloud.minecraft.extras.RichDescription.richDescription;

import dev.triassic.template.TemplateImpl;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;

/**
 * Abstract base class for commands.
 *
 * <p>This class sets up a command with a specific name, description, and permission.
 * Subclasses should implement the {@link #execute(CommandContext)}
 * method to define the command's behavior.</p>
 */
public abstract class TemplateCommand {

    protected TemplateImpl instance;

    /**
     * Constructs a new {@link TemplateCommand} with the given parameters.
     *
     * @param instance the instance of the template plugin
     * @param commandManager the {@link CommandManager} to register the command with
     * @param name the name of the command
     * @param description the description of the command
     */
    public TemplateCommand(
        final TemplateImpl instance,
        final CommandManager<CommandSource> commandManager,
        final String name,
        final Component description
    ) {
        this.instance = instance;
        var command = commandManager.commandBuilder("template")
            .literal(name)
            .senderType(CommandSource.class)
            .permission("template.command." + name)
            .commandDescription(richDescription(description))
            .handler(this::execute)
            .build();

        commandManager.command(command);
    }

    /**
     * Executes the command.
     *
     * <p>Subclasses must implement this method to define the command's behavior.</p>
     *
     * @param ctx the command context
     */
    protected abstract void execute(CommandContext<CommandSource> ctx);
}
