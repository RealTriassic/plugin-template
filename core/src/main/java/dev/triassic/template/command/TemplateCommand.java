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
