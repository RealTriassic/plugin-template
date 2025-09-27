/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import dev.triassic.template.command.Commander;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Represents a Velocity-specific {@link Commander}.
 *
 * <p>Slightly borrowed from <a href="https://github.com/Hexaoxide/Carbon">Carbon's</a> implementation.</p>
 */
@DefaultQualifier(NonNull.class)
public interface VelocityCommander extends Commander, ForwardingAudience.Single {

    /**
     * Create a new {@link VelocityCommander} from a {@link CommandSource}.
     *
     * @param sender the {@link CommandSource}
     * @return a new {@link VelocityCommander}
     */
    static VelocityCommander from(final CommandSource sender) {
        return new VelocityCommanderImpl(sender);
    }

    /**
     * Gets the underlying {@link CommandSource}.
     *
     * @return the {@link CommandSource}
     */
    CommandSource sender();

    /**
     * A record implementation of {@link VelocityCommander}.
     */
    record VelocityCommanderImpl(CommandSource sender) implements VelocityCommander {

        @Override
        public Audience audience() {
            return sender;
        }

        @Override
        public boolean hasPermission(final String permission) {
            return sender.hasPermission(permission);
        }
    }
}
