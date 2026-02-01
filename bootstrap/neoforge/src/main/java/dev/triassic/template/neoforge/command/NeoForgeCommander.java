/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.neoforge.command;

import dev.triassic.template.command.Commander;
import dev.triassic.template.neoforge.NeoForgeTemplatePlugin;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.minecraft.commands.CommandSourceStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Represents a NeoForge-specific {@link Commander}.
 *
 * <p>Slightly borrowed from <a href="https://github.com/Hexaoxide/Carbon">Carbon's</a> implementation.</p>
 */
@DefaultQualifier(NonNull.class)
public interface NeoForgeCommander extends Commander, ForwardingAudience.Single {

    /**
     * Create a new {@link NeoForgeCommander} from a {@link CommandSourceStack}.
     *
     * @param source the {@link CommandSourceStack}
     * @return a new {@link NeoForgeCommander}
     */
    static NeoForgeCommander from(final CommandSourceStack source) {
        return new NeoForgeCommanderImpl(source);
    }

    /**
     * Gets the underlying {@link CommandSourceStack}.
     *
     * @return the {@link CommandSourceStack}
     */
    CommandSourceStack source();

    /**
     * A record implementation of {@link NeoForgeCommander}.
     */
    record NeoForgeCommanderImpl(CommandSourceStack source) implements NeoForgeCommander {

        @Override
        public Audience audience() {
            return NeoForgeTemplatePlugin.getAdventure().audience(source);
        }

        @Override
        public boolean hasPermission(final String permission) {
            return true; // TODO: PermissionAPI.getPermission(source, permission)
        }
    }
}