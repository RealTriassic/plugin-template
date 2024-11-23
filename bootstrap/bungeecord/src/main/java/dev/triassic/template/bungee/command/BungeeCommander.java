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

package dev.triassic.template.bungee.command;

import dev.triassic.template.command.Commander;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Represents a command source in the Bungeecord platform that wraps a {@link net.md_5.bungee.api.CommandSender}.
 * It provides methods to interact with commands, permissions, and audiences.
 */
@DefaultQualifier(NonNull.class)
public interface BungeeCommander extends Commander, ForwardingAudience.Single {

    /**
     * Creates a new {@link BungeeCommander} instance from a given {@link net.md_5.bungee.api.CommandSender}.
     *
     * @param sender the {@link net.md_5.bungee.api.CommandSender} to wrap in a {@link BungeeCommander}.
     * @return a new {@link BungeeCommander} instance.
     */
    static BungeeCommander from(final net.md_5.bungee.api.CommandSender sender) {
        return new BungeeCommandSourceImpl(sender);
    }

    /**
     * Returns the underlying {@link net.md_5.bungee.api.CommandSender} for this {@link BungeeCommander}.
     *
     * @return the {@link net.md_5.bungee.api.CommandSender} that this source delegates to.
     */
    net.md_5.bungee.api.CommandSender commandSender();

    /**
     * Implementation of {@link BungeeCommander} that wraps a {@link net.md_5.bungee.api.CommandSender}.
     * This record delegates command-related methods to the underlying {@link net.md_5.bungee.api.CommandSender}.
     */
    record BungeeCommandSourceImpl(net.md_5.bungee.api.CommandSender commandSender) implements
        BungeeCommander
    {

        @Override
        public Audience audience() {
            return this.commandSender;
        }

        @Override
        public boolean hasPermission(final String permission) {
            return this.commandSender.hasPermission(permission);
        }
    }
}
