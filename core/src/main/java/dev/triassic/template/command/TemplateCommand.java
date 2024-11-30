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

import dev.triassic.template.TemplateImpl;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.bean.CommandBean;
import org.incendo.cloud.bean.CommandProperties;

/**
 * Abstract class that represents a command in the plugin.
 */
@Getter
public abstract class TemplateCommand extends CommandBean<Commander> {

    private TemplateImpl instance;

    /**
     * Returns the name of the command.
     */
    protected abstract @NonNull String name();

    /**
     * Returns the description of the command.
     */
    protected abstract @NonNull String description();

    /**
     * Registers the command with the provided {@link CommandManager} and {@link TemplateImpl}.
     *
     * @param instance the {@link TemplateImpl} instance
     * @param commandManager  the {@link CommandManager} to register this command with
     */
    public void register(
        final @NonNull TemplateImpl instance,
        final @NonNull CommandManager<Commander> commandManager
    ) {
        this.instance = instance;
        commandManager.command(this);
    }

    @Override
    public @NonNull CommandProperties properties() {
        return CommandProperties.of("template");
    }

    @Override
    public Command. @NonNull Builder<? extends Commander> configure(
        final Command. @NonNull Builder<Commander> builder
    ) {
        return builder
            .literal(name())
            .senderType(Commander.class)
            .permission("template.command." + name())
            .commandDescription(commandDescription(description()));
    }
}
