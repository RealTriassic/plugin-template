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

package dev.triassic.template.bukkit.command;

import dev.triassic.template.TemplatePermission;
import dev.triassic.template.command.Commander;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Represents a command source in the Bukkit platform that wraps a {@link CommandSender}.
 * It provides methods to interact with commands, permissions, and audiences.
 */
@DefaultQualifier(NonNull.class)
public interface BukkitCommander extends Commander, ForwardingAudience.Single {

    /**
     * Wraps a {@link org.bukkit.command.CommandSender} as a {@link BukkitCommander}.
     *
     * @param sender the {@link org.bukkit.command.CommandSender} to wrap.
     * @return a {@link BukkitCommander} instance.
     */
    static BukkitCommander wrap(CommandSender sender) {
        return new BukkitCommandSourceImpl(sender);
    }

    /**
     * Gets the underlying {@link org.bukkit.command.CommandSender} associated with this source.
     *
     * @return the wrapped {@link org.bukkit.command.CommandSender}.
     */
    CommandSender commandSender();

    /**
     * Implementation of {@link BukkitCommander} that delegates all operations
     * to the provided {@link org.bukkit.command.CommandSender}.
     */
    record BukkitCommandSourceImpl(CommandSender sender) implements BukkitCommander
    {

        @Override
        public Audience audience() {
            return sender;
        }

        @Override
        public boolean hasPermission(final TemplatePermission permission) {
            return sender.hasPermission(permission.getPermission());
        }
    }
}
