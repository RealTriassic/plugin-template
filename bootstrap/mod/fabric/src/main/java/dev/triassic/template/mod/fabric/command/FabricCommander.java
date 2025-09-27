/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.mod.fabric.command;

import dev.triassic.template.command.Commander;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.minecraft.commands.CommandSourceStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Represents a Fabric-specific {@link Commander}.
 *
 * <p>Slightly borrowed from <a href="https://github.com/Hexaoxide/Carbon">Carbon's</a> implementation.</p>
 */
@DefaultQualifier(NonNull.class)
public interface FabricCommander extends Commander, ForwardingAudience.Single {

    /**
     * Create a new {@link FabricCommander} from a {@link CommandSourceStack}.
     *
     * @param source the {@link CommandSourceStack}
     * @return a new {@link FabricCommander}
     */
    static FabricCommander from(final CommandSourceStack source) {
        return new FabricCommanderImpl(source);
    }

    /**
     * Gets the underlying {@link CommandSourceStack}.
     *
     * @return the {@link CommandSourceStack}
     */
    CommandSourceStack source();

    /**
     * A record implementation of {@link FabricCommander}.
     */
    record FabricCommanderImpl(CommandSourceStack source) implements FabricCommander {

        @Override
        public Audience audience() {
            return source.audience();
        }

        @Override
        public boolean hasPermission(final String permission) {
            return true; // TODO: Check for permission in Fabric.
        }
    }
}
