/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.bukkit.command;

import dev.triassic.template.bukkit.BukkitTemplatePlugin;
import dev.triassic.template.command.Commander;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Represents a Bukkit-specific {@link Commander}.
 *
 * <p>Slightly borrowed from <a href="https://github.com/Hexaoxide/Carbon">Carbon's</a> implementation.</p>
 */
@DefaultQualifier(NonNull.class)
public interface BukkitCommander extends Commander, ForwardingAudience.Single {

    /**
     * Create a new {@link BukkitCommander} from a {@link CommandSender}.
     *
     * @param sender the {@link CommandSender}
     * @return a new {@link BukkitCommander}
     */
    static BukkitCommander from(final CommandSender sender) {
        return new BukkitCommanderImpl(sender);
    }

    /**
     * Gets the underlying {@link CommandSender}.
     *
     * @return the {@link CommandSender}
     */
    CommandSender sender();

    /**
     * A record implementation of {@link BukkitCommander}.
     */
    record BukkitCommanderImpl(CommandSender sender)
        implements BukkitCommander {

        @Override
        public Audience audience() {
            return BukkitTemplatePlugin.getAdventure().sender(sender);
        }

        @Override
        public boolean hasPermission(final String permission) {
            return sender.hasPermission(permission);
        }
    }
}
