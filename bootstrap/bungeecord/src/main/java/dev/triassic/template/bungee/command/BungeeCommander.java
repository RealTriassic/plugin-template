/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.bungee.command;

import dev.triassic.template.bungee.BungeeTemplatePlugin;
import dev.triassic.template.command.Commander;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.md_5.bungee.api.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Represents a Bungeecord-specific {@link Commander}.
 *
 * <p>Slightly borrowed from <a href="https://github.com/Hexaoxide/Carbon">Carbon's</a> implementation.</p>
 */
@DefaultQualifier(NonNull.class)
public interface BungeeCommander extends Commander, ForwardingAudience.Single {

    /**
     * Create a new {@link BungeeCommander} from a {@link CommandSender}.
     *
     * @param sender the {@link CommandSender}
     * @return a new {@link BungeeCommander}
     */
    static BungeeCommander from(final CommandSender sender) {
        return new BungeeCommanderImpl(sender);
    }

    /**
     * Gets the underlying {@link CommandSender}.
     *
     * @return the {@link CommandSender}
     */
    CommandSender sender();

    /**
     * A record implementation of {@link BungeeCommander}.
     */
    record BungeeCommanderImpl(CommandSender sender)
        implements BungeeCommander {

        @Override
        public Audience audience() {
            return BungeeTemplatePlugin.getAdventure().sender(sender);
        }

        @Override
        public boolean hasPermission(final String permission) {
            return sender.hasPermission(permission);
        }
    }
}
