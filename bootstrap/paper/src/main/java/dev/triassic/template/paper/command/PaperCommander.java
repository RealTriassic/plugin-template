/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.paper.command;

import dev.triassic.template.command.Commander;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Represents a Paper-specific {@link Commander}.
 *
 * <p>Slightly borrowed from <a href="https://github.com/Hexaoxide/Carbon">Carbon's</a> implementation.</p>
 */
@DefaultQualifier(NonNull.class)
public interface PaperCommander extends Commander, ForwardingAudience.Single {

    /**
     * Create a new {@link PaperCommander} from a {@link CommandSourceStack}.
     *
     * @param source the {@link CommandSourceStack}
     * @return a new {@link PaperCommander}
     */
    static PaperCommander from(final CommandSourceStack source) {
        return new PaperCommanderImpl(source);
    }

    /**
     * Gets the underlying {@link CommandSourceStack}.
     *
     * @return the {@link CommandSourceStack}
     */
    CommandSourceStack source();

    /**
     * A record implementation of {@link PaperCommander}.
     */
    record PaperCommanderImpl(CommandSourceStack source) implements PaperCommander {

        @Override
        public Audience audience() {
            return source.getSender();
        }

        @Override
        public boolean hasPermission(final String permission) {
            return source.getSender().hasPermission(permission);
        }
    }
}
